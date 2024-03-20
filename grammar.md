# Grammar Rules

## Arithmetic 
* **expr** 
    * `VAR` varname `=` *expr*
    * *term* ((`+` | `-`) *term*)*
* **term** *factor* ((`*` | `/`) *factor*)*
* **factor** 
    * (`+` | `-`) *factor*
    * *power* 
* **power** *atom* (`^` *factor*)*
* **atom**
    * INT | FLOAT | IDENTIFIER
    * `(`*expr*`)`

## Variable
* **assign** `VAR` varname[^1] `=` *expr*

[^1]: We call the varname *identifier*.