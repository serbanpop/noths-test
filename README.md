# NOTHS Solution

## Implementation Notes
1. Prices are `BigDecimal` rather than `Double` for rounding and precision purposes
1. The type of promotions (`PRODUCT`, `ORDER`) determines the order in which the rules apply as this has an impact on the end result.
1. The checkout order is immutable so that multiple calculations yield the same result  

## Testing Approach
Integration tests treat the application as a black box. They cover all the acceptance criteria defined in the requirements, plus a few edge cases.  