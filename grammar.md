# Grammar Rules

## Arithmetic 
1. **expr** 
    1. `VAR` varname[^1]`=`*expr*
    1. *comp-expr* ((`AND`|`OR`) *comp-expr*)*
1. **comp-expr** 
    1. `NOT` *comp-expr*
    1. *arith-expr* ((`==`|`<`|`>`|`<=`|`>=`|`!=`) *arith-expr*)*
1. **arith-expr** *term* ((`+`|`-`) *term*)*
1. **term** *factor* ((`*`|`/`) *factor*)*
1. **factor** 
    1. (`+`|`-`) *factor*
    1. *power* 
1. **power** *atom* (`^`*factor*)*
1. **atom**
    1. INT | FLOAT | IDENTIFIER
    1. `(`*expr*`)`

## Logic
- **EQ** *expr*`==`*expr*
- **GT** *expr*`>`*expr*
- **LT** *expr*`<`*expr*
- **GE** *expr*`>=`*expr*
- **LE** *expr*`<=`*expr*
- **NE** *expr*`!=`*expr*
- **AND** *expr* `AND` *expr*
- **OR** *expr* `OR` *expr*
- **NOT** `NOT` *expr*
- **TRUE** `TRUE`[^2]
- **FALSE** `FALSE`[^3]

## Variable
* **assign** `VAR` varname `=` *expr*
* **access** varname

[^1]: We call the varname *identifier*.
[^2]: This is a constant.
[^3]: This is a contstant.