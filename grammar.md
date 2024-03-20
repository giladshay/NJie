# Grammar Rules

* **expr** *term* ((`+` | `-`) *term*)*
* **term** *factor* ((`*` | `/`) *factor*)*
* **factor** 
    * (`+` | `-`) *factor*
    * *power* 
* **power** *atom* (`^` *factor*)*
* **atom**
    * INT | FLOAT 
    * `(`*expr*`)`