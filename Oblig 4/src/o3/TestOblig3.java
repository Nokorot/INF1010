package o3;

import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;

/*
 * Testene forutsetter at klassenavn er som beskrevet i obligteksten,
 * at grensesnittene Tabell<T> og Liste<T> finnes, og at unntakene
 * UgyldigPlassUnntak og FullTabellUnntak finnes. Disse grensesnittene og
 * klassene er gitt i oppgaveteksten.
 *
 * Du trenger ikke sette deg inn i koden i denne filen - det holder at du
 * kompilerer filen og kjorer TestOblig3. Om det er kompileringsfeil er det
 * sannsynligvis fordi du ikke har lagt inn de 4 klassene/grensesnittene listet
 * ovenfor.
 */
public class TestOblig3 {
    private static boolean verbose = false;
    private static int antallTester = 0;
    private static int antallPasserte = 0;
    private static String ansiRed = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[31m";
    private static String ansiGrn = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[32m";
    private static String ansiClr = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[0m";

    public static void main(String[] args) {
        if (args.length > 0 && (args[0].equals("-v") || args[0].equals("--verbose"))) {
            verbose = true;
        } else {
            System.out.println("Tips: Vis alle tester som passerer med -v eller --verbose: 'java TestOblig3 -v'");
        }

        testStatiskTabell();
        testStatiskTabellIterator();
        testDynamiskTabell();
        testDynamiskTabellIterator();
        testStabel();
        testStabelIterator();
        testKoe();
        testKoeIterator();
        testOrdnetLenkeliste();
        testOrdnetLenkelisteIterator();

        System.out.printf("\n====== Oppsummering =======\n%d av %d tester OK\n", antallPasserte, antallTester);

        if (antallTester != antallPasserte) {
            System.err.printf(ansiRed + ">>>> FEIL i %d tester! <<<<\n" + ansiClr, antallTester-antallPasserte);
        }
        else {
            System.out.println(ansiGrn + ">>>> Alle tester OK! <<<<" + ansiClr);
            System.out.println("Merk: Dette er ingen garanti for at ALT er implementert riktig.");
        }
    }

    @SuppressWarnings("unchecked")
    private static void testStatiskTabell() {
        System.out.println("\n===== testStatiskTabell =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Tabell<String> statiskTabell = (Tabell<String>) Class.forName("StatiskTabell").getDeclaredConstructor(int.class).newInstance(4);
            sjekkLikhet("erTom() i tom StatiskTabell med 4 plasser", statiskTabell.erTom(), true);
            sjekkLikhet("storrelse() i tom StatiskTabell med 4 plasser", statiskTabell.storrelse(), 0);
            try {
                statiskTabell.hentFraPlass(5);
                failTest("UgyldigPlassUnntak ved hentFraPlass(5) der det bare er 4 plasser", "ingen unntak", "UgyldigPlassUnntak");
            } catch(UgyldigPlassUnntak e) { //klassen er gitt i oppgaveteksten
                passTest("UgyldigPlassUnntak ved hentFraPlass(5) der det bare er 4 plasser");
            } catch(Exception e) {
                failTest("UgyldigPlassUnntak ved hentFraPlass(5) der det bare er 4 plasser", "ingen/feil unntakshandtering", "UgyldigPlassUnntak");
            }

            statiskTabell.settInn("element 1");
            sjekkLikhet("erTom() i StatiskTabell med ett element", statiskTabell.erTom(), false);
            sjekkLikhet("storrelse() i StatiskTabell med ett element", statiskTabell.storrelse(), 1);
            sjekkLikhet("hentFraPlass(0) i StatiskTabell med ett element", statiskTabell.hentFraPlass(0), "element 1");

            statiskTabell.settInn("element 2");
            statiskTabell.settInn("element 3");
            statiskTabell.settInn("element 4");
            sjekkLikhet("erTom() i StatiskTabell med fire elementer", statiskTabell.erTom(), false);
            sjekkLikhet("storrelse() i StatiskTabell med fire elementer", statiskTabell.storrelse(), 4);
            sjekkLikhet("hentFraPlass(3) i StatiskTabell med fire elementer", statiskTabell.hentFraPlass(3), "element 4");

            try {
            	statiskTabell.settInn("element 5");
                failTest("FullTabellUnntak", "ingen unntak", "FullTabellUnntak");
            } catch(FullTabellUnntak e) { //klassen er gitt i oppgaveteksten
                passTest("FullTabellUnntak");
            } catch(Exception e) {
                failTest("FullTabellUnntak", "ingen/feil unntakshandtering", "FullTabellUnntak");
                e.printStackTrace();
            }

            try {
                statiskTabell.hentFraPlass(-1);
                failTest("UgyldigPlassUnntak ved hentFraPlass(-1)", "ingen unntak", "UgyldigPlassUnntak");
            } catch(UgyldigPlassUnntak e) {
                passTest("UgyldigPlassUnntak ved hentFraPlass(-1)");
            } catch(Exception e) {
                failTest("UgyldigPlassUnntak ved hentFraPlass(-1)", "ingen/feil unntakshandtering", "UgyldigPlassUnntak");
            }
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste StatiskTabell-iterator siden klassen ikke finnes");
            antallTester++;
        }
        catch(NoSuchMethodException e) {
            System.out.println("Kan ikke teste StatiskTabell-iterator siden konstruktoren StatiskTabell(int) ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    @SuppressWarnings("unchecked")
    private static void testStatiskTabellIterator() {
        System.out.println("\n===== testStatiskTabellIterator =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Tabell<String> statiskTabell = (Tabell<String>) Class.forName("StatiskTabell").getDeclaredConstructor(int.class).newInstance(4);
            Iterator<String> it = statiskTabell.iterator();
            sjekkLikhet("hasNext() i tom statiskTabell", it.hasNext(), false);

            statiskTabell.settInn("element1");

            it = statiskTabell.iterator();
            sjekkLikhet("hasNext() i tabell med ett element", it.hasNext(), true);
            sjekkLikhet("next() i tabell med ett element", it.next(), "element1");
            sjekkLikhet("hasNext() etter next() i tabell med ett element", it.hasNext(), false);

            statiskTabell.settInn("element2");
            statiskTabell.settInn("element3");
            statiskTabell.settInn("element4");

            it = statiskTabell.iterator();
            sjekkLikhet("at next() i en ny iterator begynner fra starten igjen", it.next(), "element1");
            sjekkLikhet("at next() fortsetter fra forste element", it.next(), "element2");
            it.next();
            sjekkLikhet("at next() kommer til siste element", it.next(), "element4");
            sjekkLikhet("hasNext() ved slutten av tabellen", it.hasNext(), false);
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste StatiskTabell-iterator siden klassen ikke finnes");
            antallTester++;
        }
        catch(NoSuchMethodException e) {
            System.out.println("Kan ikke teste StatiskTabell-iterator siden konstruktoren StatiskTabell(int) ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    @SuppressWarnings("unchecked")
    private static void testDynamiskTabell() {
        System.out.println("\n===== testDynamiskTabell =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Tabell<String> dynamiskTabell = (Tabell<String>) Class.forName("DynamiskTabell").getDeclaredConstructor(int.class).newInstance(4);
            sjekkLikhet("erTom() i tom DynamiskTabell opprettet med 'new DynamiskTabell(4)'", dynamiskTabell.erTom(), true);
            sjekkLikhet("storrelse() i tom DynamiskTabell opprettet med 'new DynamiskTabell(4)'", dynamiskTabell.storrelse(), 0);
            try {
                dynamiskTabell.hentFraPlass(0);
                failTest("UgyldigPlassUnntak ved hentFraPlass(0) i tom DynamiskTabell", "ingen unntak", "UgyldigPlassUnntak");
            } catch(UgyldigPlassUnntak e) {
                passTest("UgyldigPlassUnntak ved hentFraPlass(0) i tom DynamiskTabell");
            } catch(Exception e) {
                failTest("UgyldigPlassUnntak ved hentFraPlass(0) i tom DynamiskTabell", "ingen/feil unntakshandtering", "UgyldigPlassUnntak");
            }

            dynamiskTabell.settInn("element 1");
            sjekkLikhet("erTom() i DynamiskTabell med ett element", dynamiskTabell.erTom(), false);
            sjekkLikhet("storrelse() i DynamiskTabell med ett element", dynamiskTabell.storrelse(), 1);
            sjekkLikhet("hentFraPlass(0) i DynamiskTabell med ett element", dynamiskTabell.hentFraPlass(0), "element 1");

            dynamiskTabell.settInn("element 2");
            dynamiskTabell.settInn("element 3");
            dynamiskTabell.settInn("element 4");
            sjekkLikhet("erTom() i DynamiskTabell med 4 elementer", dynamiskTabell.erTom(), false);
            sjekkLikhet("storrelse() i DynamiskTabell med 4 elementer", dynamiskTabell.storrelse(), 4);
            sjekkLikhet("hentFraPlass(3) i DynamiskTabell med 4 elementer", dynamiskTabell.hentFraPlass(3), "element 4");
            try {
                dynamiskTabell.hentFraPlass(4);
                failTest("UgyldigPlassUnntak ved hentFraPlass(4) i DynamiskTabell med 4 initielle plasser & 4 elementer (maksindeks 3 forventet)", "ingen unntak", "UgyldigPlassUnntak");
            } catch(UgyldigPlassUnntak e) {
                passTest("UgyldigPlassUnntak ved hentFraPlass(4) i tom DynamiskTabell");
            } catch(Exception e) {
                failTest("UgyldigPlassUnntak ved hentFraPlass(4) i tom DynamiskTabell med 4 initielle plasser & 4 elementer (maksindeks 3 forventet)", "ingen/feil unntakshandtering", "UgyldigPlassUnntak");
            }

            try {
                for(int i = 5; i < 11000; i++) {
                    dynamiskTabell.settInn("element "+i);
                }
                passTest("settInn() i full DynamiskTabell");
            }
            catch(Exception e) {
                failTest("settInn() i full DynamiskTabell", "uforutsett unntak", "ingen unntak");
                e.printStackTrace();
            }
            sjekkLikhet("hentFraPlass(10123) i DynamiskTabell med 11000 elementer", dynamiskTabell.hentFraPlass(10123), "element 10124");
            try {
                dynamiskTabell.hentFraPlass(-1);
                failTest("UgyldigPlassUnntak ved hentFraPlass(-1)", "ingen unntak", "UgyldigPlassUnntak");
            } catch(UgyldigPlassUnntak e) {
                passTest("UgyldigPlassUnntak ved hentFraPlass(-1)");
            } catch(Exception e) {
                failTest("UgyldigPlassUnntak ved hentFraPlass(-1)", "ingen/feil unntakshandtering", "UgyldigPlassUnntak");
            }

            dynamiskTabell = (Tabell<String>) Class.forName("DynamiskTabell").newInstance();
            sjekkLikhet("erTom() i tom DynamiskTabell opprettet med konstruktoren uten parametre", dynamiskTabell.erTom(), true);
            sjekkLikhet("storrelse() i tom DynamiskTabell opprettet med konstruktoren uten parametre", dynamiskTabell.storrelse(), 0);
            try {
                dynamiskTabell.hentFraPlass(0);
                failTest("UgyldigPlassUnntak ved hentFraPlass(0) i tom DynamiskTabell", "ingen unntak", "UgyldigPlassUnntak");
            } catch(UgyldigPlassUnntak e) {
                passTest("UgyldigPlassUnntak ved hentFraPlass(0) i tom DynamiskTabell");
            } catch(Exception e) {
                failTest("UgyldigPlassUnntak ved hentFraPlass(0) i tom DynamiskTabell", "ingen/feil unntakshandtering", "UgyldigPlassUnntak");
            }

            try {
                for(int i = 0; i < 11000; i++) {
                    dynamiskTabell.settInn("element "+i);
                }
                passTest("settInn() i DynamiskTabell opprettet med konstruktor uten parametre");
            }
            catch(Exception e) {
                failTest("settInn() i DynamiskTabell opprettet med konstruktor uten parametre", "uforutsett unntak", "ingen unntak");
                e.printStackTrace();
            }

            sjekkLikhet("erTom() i fylt DynamiskTabell opprettet med konstruktor uten parametre", dynamiskTabell.erTom(), false);
            sjekkLikhet("storrelse() i fylt DynamiskTabell opprettet med konstruktor uten parametre", dynamiskTabell.storrelse(), 11000);
        }
        catch(ClassNotFoundException e) {
            System.out.println(">> Hopper over testing av DynamiskTabell (klassen finnes ikke)");
        }
        catch(NoSuchMethodException e) {
            System.out.println("FEIL: Kan ikke teste DynamiskTabell siden konstruktoren DynamiskTabell(int) ikke finnes");
            antallTester++;
        }
        catch(InstantiationException e) {
            System.out.println("FEIL: Kan ikke teste alt i DynamiskTabell siden konstruktoren uten parametre ikke finnes");
            antallTester++;
        }
        catch(IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (antallTester != startTester) {
            System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
        }
    }

    @SuppressWarnings("unchecked")
    private static void testDynamiskTabellIterator() {
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Tabell<String> dynamiskTabell = (Tabell<String>) Class.forName("DynamiskTabell").getDeclaredConstructor(int.class).newInstance(3);
            System.out.println("\n===== testDynamiskTabellIterator ====="); // bare print om vi faktisk kan lage iteratoren
            Iterator<String> it = dynamiskTabell.iterator();
            sjekkLikhet("hasNext() i tom DynamiskTabell", it.hasNext(), false);

            dynamiskTabell.settInn("element1");

            it = dynamiskTabell.iterator();
            sjekkLikhet("hasNext() i tabell med ett element", it.hasNext(), true);
            sjekkLikhet("next() i tabell med ett element", it.next(), "element1");
            sjekkLikhet("hasNext() etter next() i tabell med ett element", it.hasNext(), false);

            try {
                dynamiskTabell.settInn("element2");
                dynamiskTabell.settInn("element3");
                dynamiskTabell.settInn("element4");

                it = dynamiskTabell.iterator();
                sjekkLikhet("at next() i en ny iterator begynner fra starten igjen", it.next(), "element1");
                sjekkLikhet("at next() fortsetter fra forste element", it.next(), "element2");
                it.next();
                sjekkLikhet("at next() kommer til siste element", it.next(), "element4");
                sjekkLikhet("hasNext() ved slutten av tabellen", it.hasNext(), false);

                try {
                    dynamiskTabell.settInn(null);
                    dynamiskTabell.settInn("element6");
                    it = dynamiskTabell.iterator();
                    it.next(); it.next(); it.next(); it.next();
                    sjekkLikhet("hasNext() der neste element er null", it.hasNext(), true);
                    sjekkLikhet("next() der neste element er null", it.next(), null);
                    sjekkLikhet("hasNext() etter et element som er null", it.hasNext(), true);
                    sjekkLikhet("next() etter et element som er null", it.next(), "element6");
                    sjekkLikhet("hasNext() ved slutten av en tabell som inneholder null-elementer", it.hasNext(), false);
                } catch(Exception e) {
                    failTest("Innsetting og testing av null-elementer", "unntak", "ingen unntak");
                    e.printStackTrace();
                }
            }
            catch(Exception e) {
                // Feiler typisk om dynamiskTabell ikke utvides automatisk
                failTest("Innsetting og testing av elementer utover DynamiskTabell sin initielle storrelse", "unntak", "ingen unntak");
                e.printStackTrace();
            }
        }
        catch(ClassNotFoundException e) {
            if (verbose) System.out.println(">> Hopper over testing av DynamiskTabell-iterator (klassen DynamiskTabell finnes ikke)");
        }
        catch(NoSuchMethodException e) {
            System.out.println("FEIL: Kan ikke teste DynamiskTabell-iterator siden konstruktoren DynamiskTabell(int) ikke finnes");
            antallTester++;
        }
        catch(InstantiationException e) {
            System.out.println("FEIL: Kan ikke teste alt i DynamiskTabell-iterator siden konstruktoren uten parametre ikke finnes");
            antallTester++;
        }
        catch(IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (antallTester != startTester) {
            System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
        }
    }

    @SuppressWarnings("unchecked")
    private static void testStabel() {
        System.out.println("\n===== testStabel =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Liste<String> stabel = (Liste<String>) Class.forName("Stabel").newInstance();
            sjekkLikhet("storrelse() i tom Stabel", stabel.storrelse(), 0);
            sjekkLikhet("erTom() i tom Stabel", stabel.erTom(), true);
            stabel.settInn("element1");
            sjekkLikhet("storrelse() i Stabel med ett element", stabel.storrelse(), 1);
            sjekkLikhet("erTom() i Stabel med ett element", stabel.erTom(), false);
            sjekkLikhet("fjern() i Stabel med ett element", stabel.fjern(), "element1");
            sjekkLikhet("storrelse() etter innsetting+fjerning av ett element", stabel.storrelse(), 0);
            sjekkLikhet("erTom() etter innsetting+fjerning av ett element", stabel.erTom(), true);

            for (int i = 0; i < 198; i++) {
                stabel.settInn("elm"+i);
            }
            stabel.settInn("nestsiste");
            stabel.settInn("elm33"); // duplikat element, forventes at det settes inn som et nytt element
            sjekkLikhet("storrelse() etter innsetting av 200 elementer", stabel.storrelse(), 200);
            sjekkLikhet("fjern() en gang etter innsetting av 200 elementer", stabel.fjern(), "elm33");
            sjekkLikhet("erTom() etter fjerning av ett av 200 elementer", stabel.erTom(), false);
            sjekkLikhet("storrelse() etter fjerning av ett av 200 elementer", stabel.storrelse(), 199);
            sjekkLikhet("fjern() etter fjerning av to av 200 elementer", stabel.fjern(), "nestsiste");

            try {
                stabel.settInn(null);
                stabel.settInn(null);
                sjekkLikhet("fjern() null-element", stabel.fjern(), null);
                sjekkLikhet("erTom() etter fjerning av null-element", stabel.erTom(), false);
                stabel.fjern();
                sjekkLikhet("storrelse() etter innsetting+fjerning av to null-elementer", stabel.storrelse(), 198);
            }
            catch(NullPointerException e) {
                failTest("NullPointerException ved innsetting av null-elementer", "Fikk NullPointerException", "ingen NullPointerException");
                e.printStackTrace();
            }
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste Stabel siden klassen ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    @SuppressWarnings("unchecked")
    private static void testStabelIterator() {
        System.out.println("\n===== testStabelIterator =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Liste<String> stabel = (Liste<String>) Class.forName("Stabel").newInstance();
            Iterator<String> stabelIterator = stabel.iterator();
            sjekkLikhet("hasNext() i tom stabel", stabelIterator.hasNext(), false);

            // iterator-rekkefolgen er udefinert i oppaveteksten,
            // setter inn elementer slik at iterasjon begge veier funker
            stabel.settInn("el1");
            stabel.settInn("el2");
            stabel.settInn("el3");
            stabel.settInn("el2");
            stabel.settInn("el1");

            stabelIterator = stabel.iterator();
            sjekkLikhet("hasNext() i stabel med elementer", stabelIterator.hasNext(), true);
            sjekkLikhet("next() i stabel med elementer", stabelIterator.next(), "el1");
            sjekkLikhet("hasNext() etter next()", stabelIterator.hasNext(), true);

            stabelIterator = stabel.iterator();
            sjekkLikhet("at next() i en ny iterator begynner fra starten igjen", stabelIterator.next(), "el1");
            stabelIterator.next();
            sjekkLikhet("at next() fortsetter fra forste element", stabelIterator.next(), "el3");
            stabelIterator.next();
            sjekkLikhet("at next() kommer til siste element", stabelIterator.next(), "el1");
            sjekkLikhet("hasNext() ved siste element", stabelIterator.hasNext(), false);
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste Stabel-iterator siden Stabel-klassen ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    @SuppressWarnings("unchecked")
    private static void testKoe() {
        System.out.println("\n===== testKoe =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Liste<String> koe = (Liste<String>) Class.forName("Koe").newInstance();
            sjekkLikhet("storrelse() i tom Koe", koe.storrelse(), 0);
            sjekkLikhet("erTom() i tom Koe", koe.erTom(), true);

            koe.settInn("element1");
            sjekkLikhet("storrelse() i Koe med ett element", koe.storrelse(), 1);
            sjekkLikhet("erTom() i Koe med ett element", koe.erTom(), false);
            sjekkLikhet("fjern() i Koe med ett element", koe.fjern(), "element1");
            sjekkLikhet("storrelse() etter innsetting+fjerning av ett element", koe.storrelse(), 0);
            sjekkLikhet("erTom() etter innsetting+fjerning av ett element", koe.erTom(), true);

            koe.settInn("elm0"); // duplikat element, forventes at det settes inn som et nytt element
            koe.settInn("andreElement");
            for (int i = 0; i < 198; i++) {
                koe.settInn("elm"+i);
            }
            sjekkLikhet("storrelse() etter innsetting av 200 elementer", koe.storrelse(), 200);
            sjekkLikhet("fjern() etter innsetting av 200 elementer", koe.fjern(), "elm0");
            sjekkLikhet("erTom() etter fjerning av ett av 200 elementer", koe.erTom(), false);
            sjekkLikhet("storrelse() etter fjerning av ett av 200 elementer", koe.storrelse(), 199);
            sjekkLikhet("fjern() etter fjerning av to av 200 elementer", koe.fjern(), "andreElement");

            try {
                Liste<String> koe2 = (Liste<String>) Class.forName("Koe").newInstance();
                koe2.settInn(null);
                koe2.settInn("ikkeNull");
                koe2.settInn(null);
                sjekkLikhet("fjern() av et null-element", koe2.fjern(), null);
                sjekkLikhet("erTom() etter fjerning av et null-element", koe2.erTom(), false);
                koe2.fjern();
                sjekkLikhet("storrelse() etter fjerning av et null-element og et vanlig element", koe2.storrelse(), 1);
            }
            catch(NullPointerException e) {
                sjekkLikhet("NullPointerException", "Fikk NullPointerException", "ingen NullPointerException");
                e.printStackTrace();
            }
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste Koe siden klassen ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    @SuppressWarnings("unchecked")
    private static void testKoeIterator() {
        System.out.println("\n===== testKoeIterator =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Liste<String> koe = (Liste<String>) Class.forName("Koe").newInstance();
            Iterator<String> koeIterator = koe.iterator();
            sjekkLikhet("hasNext() i tom Koe", koeIterator.hasNext(), false);

            koe.settInn("el1");
            koe.settInn("el2");
            koe.settInn("el3");

            koeIterator = koe.iterator();
            sjekkLikhet("hasNext() i Koe med elementer", koeIterator.hasNext(), true);
            sjekkLikhet("next() i Koe med elementer", koeIterator.next(), "el1");
            sjekkLikhet("hasNext() etter next()", koeIterator.hasNext(), true);

            koeIterator = koe.iterator();
            sjekkLikhet("at next() i en ny iterator begynner fra starten igjen", koeIterator.next(), "el1");
            sjekkLikhet("at next() fortsetter fra forste element", koeIterator.next(), "el2");
            sjekkLikhet("at next() kommer til siste element", koeIterator.next(), "el3");
            sjekkLikhet("hasNext() ved siste element", koeIterator.hasNext(), false);
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste Koe-iterator siden Koe-klassen ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    @SuppressWarnings("unchecked")
    private static void testOrdnetLenkeliste() {
        System.out.println("\n===== testOrdnetLenkeliste =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Liste<String> ordnetliste = (Liste<String>) Class.forName("OrdnetLenkeliste").newInstance();
            sjekkLikhet("storrelse() i tom OrdnetLenkeliste", ordnetliste.storrelse(), 0);
            sjekkLikhet("erTom() i tom OrdnetLenkeliste", ordnetliste.erTom(), true);

            ordnetliste.settInn("element1");
            sjekkLikhet("storrelse() i OrdnetLenkeliste med ett element", ordnetliste.storrelse(), 1);
            sjekkLikhet("erTom() i OrdnetLenkeliste med ett element", ordnetliste.erTom(), false);
            sjekkLikhet("fjern() i OrdnetLenkeliste med ett element", ordnetliste.fjern(), "element1");
            sjekkLikhet("storrelse() etter innsetting+fjerning av ett element", ordnetliste.storrelse(), 0);
            sjekkLikhet("erTom() etter innsetting+fjerning av ett element", ordnetliste.erTom(), true);

            ordnetliste.settInn("elementC");
            ordnetliste.settInn("elementAA");
            ordnetliste.settInn("elementAA");
            ordnetliste.settInn("elementBBB");
            ordnetliste.settInn("elementD");

            sjekkLikhet("storrelse() etter innsetting av 5 elementer der to er like", ordnetliste.storrelse(), 5);
            sjekkLikhet("fjern() etter innsetting av 5 elementer", ordnetliste.fjern(), "elementAA");
            sjekkLikhet("storrelse() etter fjerning av ett av 5 elementer", ordnetliste.storrelse(), 4);
            sjekkLikhet("fjern() for andre gang der et duplikat skal hentes", ordnetliste.fjern(), "elementAA");
            sjekkLikhet("fjern() for tredje gang etter to duplikater ble fjernet", ordnetliste.fjern(), "elementBBB");
            sjekkLikhet("storrelse() etter fjerning av tre elementer", ordnetliste.storrelse(), 2);
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste Koe siden klassen ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    @SuppressWarnings("unchecked")
    private static void testOrdnetLenkelisteIterator() {
        System.out.println("\n===== testOrdnetLenkelisteIterator =====");
        int startTester = antallTester;
        int startPasserte = antallPasserte;

        try {
            Liste<String> ordnetliste = (Liste<String>) Class.forName("OrdnetLenkeliste").newInstance();
            Iterator<String> ordnetlisteIterator = ordnetliste.iterator();
            sjekkLikhet("hasNext() i tom OrdnetLenkeliste", ordnetlisteIterator.hasNext(), false);

            ordnetliste.settInn("elementC");
            ordnetliste.settInn("elementAA");
            ordnetliste.settInn("elementB");
            ordnetliste.settInn("elementB");

            ordnetlisteIterator = ordnetliste.iterator();
            sjekkLikhet("hasNext() i OrdnetListe med elementer", ordnetlisteIterator.hasNext(), true);
            sjekkLikhet("next() i OrdnetListe med elementer", ordnetlisteIterator.next(), "elementAA");
            sjekkLikhet("hasNext() etter next()", ordnetlisteIterator.hasNext(), true);

            ordnetlisteIterator = ordnetliste.iterator();
            sjekkLikhet("at next() i en ny iterator begynner fra starten igjen", ordnetlisteIterator.next(), "elementAA");
            sjekkLikhet("at next() fortsetter fra forste element", ordnetlisteIterator.next(), "elementB");
            ordnetlisteIterator.next();
            sjekkLikhet("at next() kommer til siste element", ordnetlisteIterator.next(), "elementC");
            sjekkLikhet("hasNext() ved siste element", ordnetlisteIterator.hasNext(), false);
        }
        catch(ClassNotFoundException e) {
            System.out.println("Kan ikke teste Koe siden klassen ikke finnes");
            antallTester++;
        }
        catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.printf(">> %d av %d tester OK\n", antallPasserte-startPasserte, antallTester-startTester);
    }

    public static void sjekkLikhet(String beskrivelse, String aktuellVerdi, String forventetVerdi) {
        if (forventetVerdi == null) {
            if (aktuellVerdi == null) passTest(beskrivelse);
            else failTest(beskrivelse, aktuellVerdi, forventetVerdi);
        }
        else if (forventetVerdi.compareTo(aktuellVerdi) == 0) {
            passTest(beskrivelse);
        }
        else {
            failTest(beskrivelse, aktuellVerdi, forventetVerdi);
        }
    }

    public static void sjekkLikhet(String beskrivelse, int aktuellVerdi, int forventetVerdi) {
        sjekkLikhet(beskrivelse, ""+aktuellVerdi, ""+forventetVerdi);
    }

    public static void sjekkLikhet(String beskrivelse, boolean aktuellVerdi, boolean forventetVerdi) {
        sjekkLikhet(beskrivelse, ""+aktuellVerdi, ""+forventetVerdi);
    }

    private static void passTest(String beskrivelse) {
        antallPasserte++;
        antallTester++;
        if (verbose) System.out.printf("== Tester %s: OK\n", beskrivelse);
    }

    private static void failTest(String beskrivelse, String aktuellVerdi, String forventetVerdi) {
        antallTester++;
        System.err.printf(ansiRed + "== Tester %s: FEILET\n", beskrivelse);
        System.err.printf(">>>>>>>>> FEIL -- Aktuell verdi: %s -- Forventet verdi: %s\n" + ansiClr, aktuellVerdi, forventetVerdi);
    }
}
