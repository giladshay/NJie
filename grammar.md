# Grammar Rules

* **expr** *term* ((`+` | `-`) *term*)*
* **term** *factor* ((`*` | `/`) *factor*)*
* **factor** 
    * INT | FLOAT 
    * (`+` | `-`) *factor*
    * `(`*expr*`)`