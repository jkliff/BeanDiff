BeanDiff
========

BeanDiff is a straight forward library for comparisson of (potentially) complex beans. Different from what would be expected
from a typical implementation of the equals() method, the diffing of 2 beans will present the properties on each side that were different, including nested properties, with which the user may do whatever is needed.

Envisioned use cases include:
- selection of propagation of changes based on whether some specific property was changed or not (in comparisson to some persisted version of the same object)
- referentiate to the full path of a property that was found to be different to act upon


Features:
- follows bean conventions
- handles recursive and self referential strucutres


Pending feateures:
==================

- parameterizing fields to ignore
#- explicitly say that we're only interested in firelds with getter AND setter
- when to use equals?
- comparator override callback
- mismatch callback
- success callback
