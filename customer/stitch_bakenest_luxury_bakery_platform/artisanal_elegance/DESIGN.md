---
name: Artisanal Elegance
colors:
  surface: '#fff8f3'
  surface-dim: '#e5d8c9'
  surface-bright: '#fff8f3'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#fff2e1'
  surface-container: '#f9ecdc'
  surface-container-high: '#f3e6d6'
  surface-container-highest: '#ede1d1'
  on-surface: '#211b11'
  on-surface-variant: '#4f453f'
  inverse-surface: '#362f25'
  inverse-on-surface: '#fcefdf'
  outline: '#81756e'
  outline-variant: '#d3c3bb'
  surface-tint: '#745948'
  primary: '#331f11'
  on-primary: '#ffffff'
  primary-container: '#4b3425'
  on-primary-container: '#bd9c88'
  inverse-primary: '#e3bfab'
  secondary: '#725b29'
  on-secondary: '#ffffff'
  secondary-container: '#ffdf9f'
  on-secondary-container: '#78612e'
  tertiary: '#2e220b'
  on-tertiary: '#ffffff'
  tertiary-container: '#45371e'
  on-tertiary-container: '#b5a080'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdcc7'
  primary-fixed-dim: '#e3bfab'
  on-primary-fixed: '#2a170a'
  on-primary-fixed-variant: '#5a4132'
  secondary-fixed: '#ffdf9f'
  secondary-fixed-dim: '#e1c386'
  on-secondary-fixed: '#261a00'
  on-secondary-fixed-variant: '#584413'
  tertiary-fixed: '#f7dfbc'
  tertiary-fixed-dim: '#dac3a1'
  on-tertiary-fixed: '#251a04'
  on-tertiary-fixed-variant: '#54442a'
  background: '#fff8f3'
  on-background: '#211b11'
  surface-variant: '#ede1d1'
typography:
  display-lg:
    fontFamily: Playfair Display
    fontSize: 64px
    fontWeight: '700'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  display-lg-mobile:
    fontFamily: Playfair Display
    fontSize: 40px
    fontWeight: '700'
    lineHeight: '1.2'
  headline-lg:
    fontFamily: Playfair Display
    fontSize: 48px
    fontWeight: '600'
    lineHeight: '1.2'
  headline-lg-mobile:
    fontFamily: Playfair Display
    fontSize: 32px
    fontWeight: '600'
    lineHeight: '1.3'
  headline-md:
    fontFamily: Playfair Display
    fontSize: 32px
    fontWeight: '500'
    lineHeight: '1.4'
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: 0.01em
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '600'
    lineHeight: '1.2'
    letterSpacing: 0.1em
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1.2'
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  unit: 8px
  container-max: 1440px
  gutter: 24px
  margin-desktop: 80px
  margin-mobile: 20px
  section-padding: 120px
---

## Brand & Style

This design system embodies the intersection of high-end editorial fashion and modern digital precision. The brand personality is quiet yet authoritative—prioritizing negative space and material honesty over decorative clutter. It targets a discerning audience that values the craft of baking as much as the aesthetic of the presentation.

The visual direction combines **Apple-inspired minimalism** with **Liquid Glassmorphism**. Layouts feel expansive and unhurried, using vast white space to frame products like gallery pieces. The emotional response is one of calm, indulgence, and trust, achieved through a sophisticated "frosted" interface that mimics the delicate layers of a pastry.

## Colors

The color palette is a monochromatic journey through warm, gourmand tones. **Pure White** and **Warm Cream** serve as the foundation, providing a high-key, airy atmosphere. **Luxury Brown** is used exclusively for typography and critical UI markers to ensure maximum legibility and a grounded, premium feel.

**Soft Gold** is reserved for high-intent actions and subtle accents, acting as a "signature" rather than a primary tool. The various shades of beige and sand are utilized for structural depth, creating "tone-on-tone" layering that avoids the harshness of traditional grey-scale UI.

## Typography

This design system utilizes a high-contrast typographic pairing to reinforce the "editorial luxury" narrative. **Playfair Display** provides the romantic, sophisticated voice of the brand, used for large display headings and product titles. For headers, tight letter-spacing is used to maintain a modern, "Vogue-esque" feel.

**Inter** serves as the functional workhorse. It is set with generous line heights and slight positive letter-spacing for smaller labels to ensure effortless readability. Use **Label-LG** for navigation and category tabs, always in uppercase with tracked-out spacing to evoke premium branding.

## Layout & Spacing

The layout philosophy follows a **Fixed Grid** model for desktop, centered within a maximum container width of 1440px. The spacing rhythm is intentionally oversized—vertical gaps between major sections should never fall below 120px on desktop to maintain the "luxury of space."

We utilize a 12-column grid with generous 24px gutters. Elements should feel "unanchored" through the use of wide margins (80px on desktop). On mobile, the grid collapses to 4 columns, and section padding is reduced to 60px, ensuring the product remains the focal point without feeling cramped.

## Elevation & Depth

Depth in this design system is created through **Glassmorphism** and soft, multi-layered shadows rather than traditional solid fills. 

1.  **Liquid Glass:** Surfaces use a 15% to 40% opacity White fill with a high-intensity Backdrop Blur (20px–40px). 
2.  **Translucent Borders:** Every glass card must have a 1px border. The top and left borders are set to 20% opacity White (simulating light catch), while the bottom and right are 10% Luxury Brown (simulating a soft contact shadow).
3.  **Shadows:** We use "Ambient Soft Shadows"—large blur radii (30px+) with very low opacity (5%–8%) using the Luxury Brown color as a tint to maintain warmth.
4.  **Z-Axis:** Components move toward the user on hover through a slight increase in backdrop blur and scale (1.02x), never through heavy dark shadows.

## Shapes

The shape language is defined by the **32px rounded corner**, which is applied to all primary cards, buttons, and image containers. This generous radius softens the minimalist aesthetic, making the interface feel organic and approachable.

Small components like chips and tags follow a pill-shaped geometry to contrast with the large architectural cards. Micro-elements like checkboxes should maintain a smaller 4px radius to preserve precision.

## Components

### Buttons
Primary buttons are solid **Luxury Brown** with White text, featuring 32px rounded corners. Secondary buttons use a **Glassmorphic** style (frosted background) with a thin Gold border. Interactions should be "viscous"—slow, smooth transitions of 300ms or more.

### Cards
Product cards are the centerpiece. They utilize a frosted glass background with a 1px translucent border. Images within cards should have a subtle zoom-on-hover effect. Content inside cards is bottom-aligned to create a sophisticated, unbalanced editorial look.

### Input Fields
Inputs are minimal: a single 1px line in **Light Mocha** that expands into a soft glass container upon focus. Labels remain floating above the input in **Label-SM** style.

### Micro-interactions
All transitions should use a "Custom Ease" (e.g., `cubic-bezier(0.2, 0, 0, 1)`) to mimic the smooth, effortless movement of liquid. Loading states should utilize shimmering gradients of Warm Cream and Champagne Beige rather than mechanical spinners.

### Additional Components
*   **The Breadcrumb Trail:** Minimalist text-only navigation using Inter 12px, separated by thin 1px vertical lines.
*   **Floating Cart:** A permanent frosted glass circle or pill at the bottom right, housing the total item count in Gold.