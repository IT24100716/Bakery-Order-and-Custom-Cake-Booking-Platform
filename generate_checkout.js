const fs = require('fs');
let html = fs.readFileSync('src/main/resources/static/checkout.html', 'utf8');

// Change Title
html = html.replace(/<title>Checkout — BakeNest<\/title>/, '<title>Custom Cake Checkout — BakeNest</title>');

// Remove COD
html = html.replace(/<div class="pay-opt">\s*<input type="radio" name="payment" id="pay-cod" value="COD" checked>[\s\S]*?<\/div>/, '');

// Make CARD default
html = html.replace(/<input type="radio" name="payment" id="pay-card" value="CARD">/, '<input type="radio" name="payment" id="pay-card" value="CARD" checked>');

// Change JS logic completely for Custom Cake
const oldScriptPattern = /<script>[\s\S]*?<\/script>/g;
let scripts = [...html.matchAll(oldScriptPattern)];
const jsCode = scripts[scripts.length - 1][0];

const newScript = `
<script>
    const token = sessionStorage.getItem('token');
    if (!token) { 
        localStorage.setItem('redirectAfterLogin', window.location.pathname + window.location.search); 
        window.location.href = '/auth.html'; 
    }
    
    const urlParams = new URLSearchParams(window.location.search);
    const customId = urlParams.get('id');
    if (!customId) {
        window.location.href = '/my-orders.html';
    }

    let customCake = null;
    let subtotal = 0;

    async function fetchCustomCake() {
        try {
            const res = await fetch('/api/v1/customer/custom-cakes', {
                headers: { 'Authorization': 'Bearer ' + token }
            });
            if (res.ok) {
                const requests = await res.json();
                customCake = requests.find(r => r.id == customId);
                if (!customCake || customCake.status !== 'QUOTED') {
                    alert('Invalid custom cake request or it is not ready for payment.');
                    window.location.href = '/my-orders.html';
                    return;
                }
                initCheckout();
            } else {
                throw new Error('Failed to fetch request details.');
            }
        } catch (e) {
            console.error(e);
            alert('Failed to load checkout details.');
        }
    }

    function initCheckout() {
        subtotal = customCake.quotedPrice || 0;
        const imageUrl = customCake.referenceImageUrl || 'https://images.unsplash.com/photo-1535141192574-5d4897c12636?q=80';
        
        document.getElementById('checkout-items').innerHTML = \`<div class="s-item">
            <div class="s-img-wrap"><img class="s-img" src="\${imageUrl}"><span class="s-qty">1</span></div>
            <span class="s-name">\${customCake.flavor} Custom Creation (\${customCake.size})</span>
            <span class="s-price">Rs. \${subtotal.toFixed(2)}</span>
        </div>\`;
        
        document.getElementById('summary-subtotal').textContent = 'Rs. ' + subtotal.toFixed(2);

        let deliveryFee = 200;
        function updateTotal() {
            const sel = document.querySelector('input[name="delivery"]:checked');
            deliveryFee = sel ? parseInt(sel.dataset.fee) : 200;
            document.getElementById('summary-delivery').textContent = deliveryFee === 0 ? 'Free' : 'Rs. ' + deliveryFee.toFixed(2);
            document.getElementById('summary-total').textContent = 'Rs. ' + (subtotal + deliveryFee).toFixed(2);
            const addressWrap = document.getElementById('address-wrap');
            if(addressWrap) addressWrap.style.display = (sel && sel.value === 'PICKUP') ? 'none' : 'block';
        }
        document.querySelectorAll('input[name="delivery"]').forEach(r => r.addEventListener('change', updateTotal));
        updateTotal();

        // Toggle Payment Details
        function updatePaymentUI() {
            const method = document.querySelector('input[name="payment"]:checked').value;
            document.getElementById('card-details').classList.toggle('show', method === 'CARD');
            document.getElementById('bank-details').classList.toggle('show', method === 'BANK_TRANSFER');
        }
        document.querySelectorAll('input[name="payment"]').forEach(r => r.addEventListener('change', updatePaymentUI));
        updatePaymentUI();

        // Slip Preview
        const slipFile = document.getElementById('slip-file');
        const slipPreview = document.getElementById('slip-preview');
        slipFile.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = e => {
                    slipPreview.src = e.target.result;
                    slipPreview.style.display = 'block';
                };
                reader.readAsDataURL(file);
            }
        });

        const btnSubmit = document.getElementById('btn-submit');
        const errorBox = document.getElementById('error-box');
        const errorText = document.getElementById('error-text');

        btnSubmit.addEventListener('click', async () => {
            errorBox.classList.remove('show');
            const address = document.getElementById('input-address').value.trim();
            const phone = document.getElementById('input-phone').value.trim();
            const deliveryMethod = document.querySelector('input[name="delivery"]:checked').value;
            const paymentMethod = document.querySelector('input[name="payment"]:checked').value;

            // Validations
            if (!phone) { showErr('Please provide your phone number.'); return; }
            if (!/^(07[0-9]{8})$/.test(phone)) { showErr('Please enter a valid 10-digit Sri Lankan phone number (e.g. 0771234567).'); return; }
            if (deliveryMethod !== 'PICKUP' && !address) { showErr('Please provide your delivery address.'); return; }

            const payload = {
                paymentMethod, 
                shippingAddress: deliveryMethod === 'PICKUP' ? 'PICKUP' : address,
                phone, 
                deliveryMethod, 
                deliveryFee,
                paymentSlipUrl: null
            };

            // Handle Payment Specifics
            if (paymentMethod === 'CARD') {
                const num = document.getElementById('card-num').value.trim();
                const exp = document.getElementById('card-expiry').value.trim();
                const cvv = document.getElementById('card-cvv').value.trim();
                if (!num || !exp || !cvv) { showErr('Please enter card details.'); return; }
                if (num.length !== 16) { showErr('Please enter a valid 16-digit card number.'); return; }
                if (!/^[0-9]{2}\\/[0-9]{2}$/.test(exp)) { showErr('Please enter expiry in MM/YY format.'); return; }
                if (cvv.length !== 3) { showErr('Please enter a valid 3-digit CVV.'); return; }
            } else if (paymentMethod === 'BANK_TRANSFER') {
                const file = slipFile.files[0];
                if (!file) { showErr('Please upload your payment slip.'); return; }
                
                const formData = new FormData();
                formData.append('file', file);
                
                try {
                    const uploadRes = await fetch('/api/v1/uploads/payment-slip', {
                        method: 'POST',
                        headers: { 'Authorization': 'Bearer ' + token },
                        body: formData
                    });
                    if (!uploadRes.ok) throw new Error('Slip upload failed.');
                    payload.paymentSlipUrl = await uploadRes.text();
                } catch (e) {
                    showErr('Failed to upload payment slip.');
                    resetBtn();
                    return;
                }
            }

            btnSubmit.disabled = true;
            btnSubmit.innerHTML = '<span class="material-symbols-outlined" style="font-size:1rem;animation:spin 1s linear infinite">autorenew</span> Processing...';
            
            try {
                const res = await fetch(\`/api/v1/customer/custom-cakes/\${customId}/pay\`, { 
                    method: 'POST', 
                    headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token }, 
                    body: JSON.stringify(payload) 
                });
                
                if (res.ok) {
                    window.location.href = '/my-orders.html';
                } else {
                    const e = await res.json().catch(() => ({ message: 'Server error' }));
                    showErr(e.message || 'Failed to process payment.');
                    resetBtn();
                }
            } catch (e) {
                showErr('Connection error. Please try again.');
                resetBtn();
            }
        });

        function showErr(m) { errorText.textContent = m; errorBox.classList.add('show'); window.scrollTo({ top: 0, behavior: 'smooth' }); }
        function resetBtn() { btnSubmit.disabled = false; btnSubmit.innerHTML = '<span class="material-symbols-outlined" style="font-size:1rem">lock</span> Confirm & Pay'; }
    }

    fetchCustomCake();
</script>
`;

html = html.replace(jsCode, newScript);

// Update button text
html = html.replace(/<span class="material-symbols-outlined" style="font-size:1rem">lock<\/span> Place Order/, '<span class="material-symbols-outlined" style="font-size:1rem">lock</span> Confirm & Pay');

fs.writeFileSync('src/main/resources/static/custom-checkout.html', html);
console.log('custom-checkout.html created successfully');
