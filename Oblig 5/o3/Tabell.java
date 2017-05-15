package o3;

public interface Tabell<T> extends Iterable<T> {
    /**
     * Beregner antall elementer i tabellen
     * @return      antall elementer i tabellen
     */
    public int size();

    /**
     * Sjekker om tabellen er tom
     * @return      om tabellen er tom
     */
    public boolean erTom();

    /**
     * Setter inn et element i tabellen
     * @param   element             elementet som settes inn
     * @throws  FullTabellUnntak    hvis tabellen allerede er full
     */
    public void settInn(T element);
    
    /**
     * Fjerner element fra tabelen
     * @param   element             elementet som settes inn
     * @throws  FullTabellUnntak    hvis tabellen allerede er full
     */
    public void fjern(int index);
    
    /**
     * Fjerner dette element fra tabelen
     * @param   element             elementet som settes inn
     * @throws  FullTabellUnntak    hvis tabellen allerede er full
     */
    public void fjern(T element);

    /**
     * Henter (uten aa fjerne) et element fra tabellen
     * @param  plass    plassen i tabellen som det hentes fra
     * @return          elementet paa plassen
     * @throws  UgyldigPlassUnntak  hvis plassen ikke er en gyldig
                                    indeks i arrayet eller plassen
                                    ikke inneholder noe element
     */
    public T get(int plass);
    
    public T[] asArray();
}
