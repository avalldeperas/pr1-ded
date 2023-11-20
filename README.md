# DS - PR1

## Considerations

- java 8 or more

## Changes

- public class OrderedVector<T> extends Vector<T> per poder tenir elementAt?
- ús de DictionaryArrayImpl<> per a key value de workers, company i job offers per a reutilitzar l'implementació.
- Removed 'public' modifier from CTTCompaniesJobs as it's redundant for
- instantiation java defaults to 0 and nulls...

## Missing tests

- polling enrollments and substitutes (worker ends job offer)
- add ratings by just enrolled workers, not substitutes
- not tested max values for each data structure.
- Only enrolled workers (NOT susbstitutes) be able to add ratings?
