IntelliJ plugin - Code generator

## Provides action for generate code from custom templates

### Features

* Export and import templates to IDEA
* Creates custom template with Groovy language using

### Documentation

#### How open code generator?

Right-click, select generate option and next select code option

#### Imports templates

1. Click **import** button
2. Select file with templates (file must be with json extension)
3. Select templates to import

#### Exports templates

1. Click **export** button
2. Select catalog to save
3. Select templates to export

#### Creates new template

1. Click **Add button**
2. Fill name and type of template
3. Fill init script tab, script tab and parameters tab
8. Click **Save button**

#### Script tab

In the script tab may write custom code to generate

* The script must be written in Java language
* Can add custom variables, example $(FIELD_NAME)

#### Init script tab

In the init script tab may fill variables for script

* The init script must be written in Groovy language
* Available variables
    * activeFile (file with caret inside)
    * activeClass (class with caret inside)
    * selectedField (selected field by caret)
    * selectedType (selected type by caret)
    * selectedParameter (selected parameter by caret)
    * selectedLiteral (selected literal by caret)
    * parameters (parameters filled manual)
    * variables (script variables)
    
#### Parameters

In the parameters tab may select script variables to fill manual

### Examples

**Template name:** Optional getter
**Template type:** Method
**Script:**

```
public Optional<${FIELD_TYPE}> get${METHOD_SUFFIX}() {
    return Optional.ofNullable(${FIELD_NAME});
}
```

**Init script:**

```
if (selectedField) {
    variables["FIELD_NAME"] = selectedField.name
    variables["FIELD_TYPE"] = selectedField.type.canonicalText
    variables["METHOD_SUFFIX"] = selectedField.name.capitalize()
}
```

---

**Template name:** CRUD methods
**Template type:** Method

**Script:**

```
@PostMapping
public ${MODEL} create(@RequestBody ${MODEL} ${MODEL_NAME}) {
    return ${MODEL_NAME}Service.create(${MODEL_NAME});
}

@PutMapping
public ${MODEL} create(@RequestBody ${MODEL} ${MODEL_NAME}) {
    return ${MODEL_NAME}Service.update(${MODEL_NAME});
}

@DeleteMapping("{id}")
public void delete(@PathVariable Long id) {
    ${MODEL_NAME}Service.delete(id);
}

@GetMapping("{id}")
public ${MODEL} get(@PathVariable Long id) {
    return ${MODEL_NAME}Service.getById(id);
}
```

**Init script:**

```
variables["MODEL"] = parameters["MODEL"]
variables["MODEL_NAME"] = parameters["MODEL"].toLowerCase()
```

**Parameters:**

* MODEL

---

**Template name:** Entity column
**Template type:** Annotation

**Script:**

```
@Column(name = "${NAME}")
```

**Init script:**

```
import com.google.common.base.CaseFormat

if (selectedField) {
    variables["NAME"] = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, selectedField.name);
}
```