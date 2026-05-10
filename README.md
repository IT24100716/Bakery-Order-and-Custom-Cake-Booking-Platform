# BakeNest: Enterprise Bakery Order & Custom Cake Booking Platform

## 1. Project Overview & Tech Stack
**BakeNest** is a production-ready Bakery Order and Custom Cake Booking Platform.
- **Backend:** Java 17, Spring Boot 4.x, Spring Security, JWT, Spring Data JPA (Hibernate), MySQL, Lombok.
- **Frontend:** React.js, Tailwind CSS, Axios, React Router, Redux Toolkit (or Context API).
- **Core Features:** User Authentication, Product Catalog, Custom Cake Builder, Shopping Cart, Checkout & Payment Integration, Order Tracking, Admin Dashboard, Image Uploads.

## 2. System Architecture
- **Client-Server Architecture:** React SPA communicates with Spring Boot REST APIs via JSON.
- **Security:** Stateless authentication using JWT. Passwords hashed using BCrypt. Role-Based Access Control (RBAC) for `USER` and `ADMIN`.
- **API Design:** RESTful principles, versioning (e.g., `/api/v1/`), standard HTTP methods, and appropriate status codes. Global Exception Handling using `@ControllerAdvice`.

## 3. Database Design (Entity Relationships)
- **User:** `id`, `name`, `email`, `password`, `role`, `phone`, `address`, `created_at`
- **Product:** `id`, `name`, `description`, `price`, `category`, `stock`, `image_url`
- **CustomCakeRequest:** `id`, `user_id`, `flavor`, `size`, `message`, `reference_image_url`, `status`, `quoted_price`
- **Order:** `id`, `user_id`, `total_amount`, `status` (PENDING, PAID, BAKING, OUT_FOR_DELIVERY, DELIVERED), `payment_status`, `created_at`
- **OrderItem:** `id`, `order_id`, `product_id`, `quantity`, `price`

## 4. Folder Structures

### Backend (Spring Boot)
```text
src/main/java/bake/nest/
├── BakeNestApplication.java
├── config/       # SecurityConfig, CorsConfig, CloudinaryConfig
├── controller/   # AuthController, ProductController, OrderController
├── dto/          # Requests & Responses (LoginRequest, ProductDto)
├── entity/       # JPA Entities (User, Product, Order)
├── exception/    # GlobalExceptionHandler, Custom Exceptions
├── repository/   # Spring Data JPA Repositories
├── security/     # JwtTokenProvider, JwtAuthenticationFilter, CustomUserDetailsService
└── service/      # Business Logic (AuthService, ProductService)
```

### Frontend (React)
```text
src/
├── assets/       # Images, global styles
├── components/   # Reusable UI (Navbar, Footer, Button, Card)
├── context/      # AuthContext, CartContext
├── hooks/        # Custom hooks (useAuth, useCart)
├── pages/        # Home, Login, Catalog, ProductDetails, Cart, Checkout, AdminDashboard
├── services/     # Axios API calls (auth.service.js, api.js)
└── utils/        # Helpers (validation, formatting)
```

## 5. Step-by-Step Implementation Roadmap

### Phase 1: Backend Foundation & Security
1. **Setup & Configuration:** Initialize Spring Boot with MySQL properties.
2. **Entity & Repository:** Create `User` entity and `UserRepository`.
3. **Security Configuration:** Implement JWT Utility class, JWT Filter, and configure Spring Security (`SecurityFilterChain`).
4. **Auth Module:** Build `AuthController` (`/api/v1/auth/register`, `/login`) and `AuthService`.
5. **Validation & Exceptions:** Add `@Valid` constraints on DTOs and set up `@ControllerAdvice` for consistent error responses.

### Phase 2: Core Domain Logic (Backend)
1. **Product Management:** Create `Product` entity. Implement CRUD operations restricted to ADMIN. Allow public read access to product lists.
2. **Custom Cake Module:** Implement API endpoints for users to submit custom requests, and for admins to review/quote prices.
3. **Image Upload:** Integrate Cloudinary (or AWS S3) for uploading cake reference images and product photos.
4. **Order Management:** Build `Order` and `OrderItem` entities. Implement cart checkout endpoint.

### Phase 3: Frontend Foundation
1. **React Setup:** Initialize Vite + React project. Install Tailwind CSS, Axios, React Router Dom.
2. **Routing & Layout:** Set up `BrowserRouter`, Public routes, and Protected routes (`<ProtectedRoute>`).
3. **State Management:** Set up Redux or Context API to manage User session and Shopping Cart state.
4. **API Service Layer:** Configure Axios instance with an interceptor to automatically attach the `Authorization: Bearer <token>` header to requests.

### Phase 4: Frontend Development (User & Admin)
1. **Authentication UI:** Build Login and Registration forms with Formik/Yup validation. Handle JWT storage (localStorage/secure cookies).
2. **Customer Panel:** 
   - Home page showcasing featured items.
   - Product Catalog with search/filter.
   - Custom Cake Builder form (with file upload).
   - Shopping Cart and Checkout flow.
3. **Admin Dashboard:**
   - Manage Products (Create, Update, Delete).
   - View and manage User Orders (Change order status).
   - Review and price Custom Cake Requests.

### Phase 5: Advanced Features & Refinement
1. **Payment Integration:** Integrate Stripe or PayPal API for checkout. (Simulate with test keys first).
2. **Email Notifications:** Integrate JavaMailSender or Resend/SendGrid API to send Order Confirmations.
3. **Pagination & Sorting:** Update Backend repositories to use `Pageable` and implement pagination on the Frontend catalog.
4. **Analytics:** Add basic chart aggregations (e.g., Sales this month) to the Admin Dashboard.

### Phase 6: Testing & Deployment
1. **Testing:** Write Unit Tests (JUnit/Mockito) for backend services. Write basic integration tests for APIs.
2. **CI/CD Pipeline:** Set up GitHub Actions to build the backend and frontend.
3. **Deployment:** 
   - Backend: Dockerize Spring Boot app, deploy to Railway, Render, or AWS ECS.
   - Database: Provision managed MySQL on AWS RDS or Railway.
   - Frontend: Build React app and deploy to Vercel or Netlify.

## 6. Real-World Best Practices Incorporated
- **DTO Pattern:** Never expose database entities directly to the client.
- **Statelessness:** Scale the backend easily by avoiding server-side sessions.
- **CORS Configuration:** Strictly define allowed origins, headers, and methods.
- **Environment Variables:** Keep secrets (JWT secret, DB password, API keys) out of source code.
- **Responsive Design:** Ensure the React frontend is fully mobile-responsive using Tailwind utility classes.
