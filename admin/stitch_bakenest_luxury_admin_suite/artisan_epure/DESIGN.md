---
name: Artisan Epure
colors:
  surface: '#fff8f5'
  surface-dim: '#f7d3bd'
  surface-bright: '#fff8f5'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#fff1ea'
  surface-container: '#ffeade'
  surface-container-high: '#ffe3d3'
  surface-container-highest: '#ffdcc7'
  on-surface: '#2a170a'
  on-surface-variant: '#4a463f'
  inverse-surface: '#412b1d'
  inverse-on-surface: '#ffede4'
  outline: '#7c766e'
  outline-variant: '#cdc5bc'
  surface-tint: '#625e59'
  primary: '#625e59'
  on-primary: '#ffffff'
  primary-container: '#fff8f1'
  on-primary-container: '#76726c'
  inverse-primary: '#cbc5bf'
  secondary: '#6d5c40'
  on-secondary: '#ffffff'
  secondary-container: '#f4dcb9'
  on-secondary-container: '#726044'
  tertiary: '#725b29'
  on-tertiary: '#ffffff'
  tertiary-container: '#fff8f1'
  on-tertiary-container: '#876f3a'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#e8e1db'
  primary-fixed-dim: '#cbc5bf'
  on-primary-fixed: '#1e1b17'
  on-primary-fixed-variant: '#4a4641'
  secondary-fixed: '#f7dfbc'
  secondary-fixed-dim: '#dac3a1'
  on-secondary-fixed: '#251a04'
  on-secondary-fixed-variant: '#54442a'
  tertiary-fixed: '#ffdf9f'
  tertiary-fixed-dim: '#e1c386'
  on-tertiary-fixed: '#261a00'
  on-tertiary-fixed-variant: '#584413'
  background: '#fff8f5'
  on-background: '#2a170a'
  surface-variant: '#ffdcc7'
typography:
  display-lg:
    fontFamily: Bodoni Moda
    fontSize: 64px
    fontWeight: '600'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Bodoni Moda
    fontSize: 48px
    fontWeight: '500'
    lineHeight: '1.2'
  headline-md:
    fontFamily: Bodoni Moda
    fontSize: 32px
    fontWeight: '500'
    lineHeight: '1.3'
  headline-sm:
    fontFamily: Bodoni Moda
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.4'
  body-lg:
    fontFamily: Hanken Grotesk
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: 0.01em
  body-md:
    fontFamily: Hanken Grotesk
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: 0.01em
  label-md:
    fontFamily: Hanken Grotesk
    fontSize: 14px
    fontWeight: '600'
    lineHeight: '1.2'
    letterSpacing: 0.05em
  headline-lg-mobile:
    fontFamily: Bodoni Moda
    fontSize: 36px
    fontWeight: '500'
    lineHeight: '1.2'
rounded:
  sm: 0.5rem
  DEFAULT: 1rem
  md: 1.5rem
  lg: 2rem
  xl: 3rem
  full: 9999px
spacing:
  unit: 4px
  xs: 8px
  sm: 16px
  md: 24px
  lg: 48px
  xl: 80px
  container-padding: 64px
  gutter: 32px
---

## Brand & Style

This design system embodies the intersection of culinary artistry and high-end technology. The personality is curated, serene, and sophisticated—evoking the atmosphere of a Michelin-starred patisserie. 

The visual language utilizes **Liquid Glassmorphism**, characterized by hyper-smooth gradients, multi-layered translucency, and substantial backdrop blurs (30px-50px). The aesthetic draws heavily from Apple’s editorial minimalism: utilizing negative space as a luxury commodity rather than a void to be filled. Surfaces should feel like frosted crystal, catching "light" from the top-left to create a subtle sense of physical depth without traditional skeuomorphism.

## Colors

The palette is anchored in organic, warm neutrals that reflect high-quality ingredients (flour, cream, cane sugar). 

- **Foundation:** Pure White (#FFFFFF) is reserved for high-level background containers and light-bleed effects. Warm Cream (#FFF8F1) serves as the standard page background to reduce eye strain and provide a more "bespoke" feel.
- **Accents:** A hierarchy of beiges (Light Beige to Champagne) provides tonal depth for nested components. 
- **Detailing:** Soft Gold (#CBAE73) is used sparingly for interactive states, luxury markers, and premium call-to-actions.
- **Typography:** Luxury Brown (#4B3425) provides a softer, more sophisticated contrast than pure black, maintaining readability while feeling integrated into the warm palette.

## Typography

This design system uses a high-contrast typographic pairing to signal luxury. 

- **Headlines:** `Bodoni Moda` provides the editorial authority. Use tight tracking for large display sizes to emphasize its elegant vertical stress.
- **Body & Interface:** `Hanken Grotesk` offers a clean, contemporary counterpoint. It should be typeset with generous leading (1.6) and slightly increased letter spacing (tracking) to maintain the airy, Apple-inspired aesthetic.
- **Labels:** Small labels and UI micro-copy should use Hanken Grotesk in Semi-bold with uppercase styling to provide clear hierarchy in dense data views.

## Layout & Spacing

The layout philosophy follows a **Fixed-Fluid Hybrid** model. On desktop, the dashboard content is housed in a centered container with a maximum width of 1600px, flanked by generous safe areas to maintain an editorial feel.

- **Grid:** A 12-column grid with wide 32px gutters. Elements typically span larger blocks (4, 6, or 12 columns) to avoid visual clutter.
- **Rhythm:** An 8px linear scale drives the spacing, but major sections utilize "Macro-spacing" (80px+) to separate distinct functional areas.
- **Responsiveness:** On mobile, margins reduce to 20px, and the 12-column grid collapses to 1-column for cards and 2-column for small metrics. Sidebars transition to full-screen glass overlays.

## Elevation & Depth

Depth is achieved through a combination of backdrop filters and "Atmospheric Shadows."

1.  **Level 0 (Base):** Warm Cream background.
2.  **Level 1 (Sub-surface):** Inset soft-shadows for input fields and wells to create a "carved" look.
3.  **Level 2 (Standard Card):** White background at 60% opacity with a 40px backdrop-blur. 1px solid border in White (40% opacity). Shadow: `0 8px 32px rgba(75, 52, 37, 0.04)`.
4.  **Level 3 (Floating Menus/Modals):** White background at 80% opacity with a 50px backdrop-blur. Shadow: `0 24px 64px rgba(75, 52, 37, 0.08)`.

Transitions between levels must be animated with a custom cubic-bezier (0.2, 0.8, 0.2, 1) to simulate the smooth movement of fluid glass.

## Shapes

The shape language is exceptionally soft and organic. 
- **Large Containers:** Dashboard cards and the primary sidebar use a `36px` radius.
- **Standard Components:** Buttons and inputs use a `28px` radius, maintaining a consistent hyper-rounded aesthetic.
- **Interactive Elements:** Active states often utilize "pill" shapes (full radius) to clearly distinguish them from structural containers.
- **Borders:** Every border is a "Thin Glass" stroke—1px wide, translucent white, designed to catch the light at the edges of the frosted surfaces.

## Components

### Buttons
Primary buttons use the Soft Gold gradient with white typography. Secondary buttons are "Ghost Glass"—transparent with a 1px translucent white border and Luxury Brown text. All buttons feature a 10% scale-up on hover with a soft shadow expansion.

### Floating Sidebars
The sidebar does not touch the edges of the screen. It is a floating glass vertical "island" with a 36px border radius, anchored 24px from the left and top/bottom edges.

### Transparent Tables
Tables remove traditional row borders. Instead, they use alternating subtle cream tints or very thin (0.5px) separators in Sand Beige. The header row uses the Label-MD typography style with increased letter spacing.

### Glass Form Inputs
Inputs are semi-transparent with a 28px radius. Upon focus, the backdrop blur increases and the border transitions to Soft Gold. The placeholder text uses a muted version of Luxury Brown.

### Cinematic Product Cards
Product imagery should be isolated with soft, layered "Product Shadows" that make items appear to hover over the glass surfaces. The card itself should have a slight inner glow to mimic the look of liquid glass edges.