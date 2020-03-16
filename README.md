# Doktor: Kotlin/TornadoFX Compartmental Disease Modeling

## Example Disease Model
```yaml
---
  metadata:
    title: Virus
    identifier: virus
    type: compartmental_disease_model
  variables:
    beta: 0.38
    gamma: 0.047
  compartments:
    - susceptible
    - infectious
    - recovered
  edges:
    -
      from: susceptible
      to: infectious
      multiple: beta
      of: infectious
    -
      from: infectious
      to: recovered
      multiple: gamma
```

## Example Mobility Graph
```yaml
---
  metadata:
    type: mobility_graph
    identifier: mobility_graph
    title: Mobility Graph
  nodes:
    -
      identifier: usa
      population: 300000000
      x: -101.459046
      y: 39.943345
    -
      identifier: italy
      population: 60000000
      x: 12.439954
      y: 42.991262
  edges:
    -
      from: usa
      to: italy
      constant: 20000
      mode: air
    -
      from: italy
      to: usa
      constant: 20000
      mode: air
```

## Simulation Example
```yaml
---
  metadata:
    type: simulation
    identifier: simulation
    title: Virus Simulation
  compartmental_disease_model: virus
  mobility_graph: mobility_graph
  initial_conditions:
    -
      set_initial_value: 20000
      of_compartment: infectious
      in_node: italy
  actions:
    -
      title: Vaccination
      update_variables: [beta]
      of: [usa]
      to: [0]
      after: 60
    -
      title: Travel restrictions
      update_mobilities: [air]
      from: [italy]
      to: [usa]
      with_rate: 0
      after: 30
```