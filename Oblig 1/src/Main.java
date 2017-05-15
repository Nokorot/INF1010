import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
	 	System.out.println("Hovedprogram: ");
		hovedprogram();
		
		System.out.println("\nExtraoppgave: ");
		extraoppgave();
	}

	static void hovedprogram(){
		Regneklynge abel = new Regneklynge(12);

		for (int i = 0; i < 650; i++)
			abel.settInnNode(new Node(64, 2.6f, 8, 8));

		for (int i = 0; i < 16; i++)
			abel.settInnNode(new Node(1024, 2.3f, 8, 8));
		
		System.out.printf("Samlet FLOPS: %.2f\n", abel.flops());
		System.out.println("Noder med minst 32 GB: " + abel.noderMedNokMinne(32));
		System.out.println("Noder med minst 64 GB: " + abel.noderMedNokMinne(64));
		System.out.println("Noder med minst 128 GB: " + abel.noderMedNokMinne(128));
		System.out.println("Antall rack: " + abel.anntalRack());
	}
	
	private static void extraoppgave() {
		try {
			Regneklynge abel2 = Regneklynge.fraFil("abel2");
			
			System.out.printf("Samlet FLOPS: %.2f\n", abel2.flops());
			System.out.println("Noder med minst 32 GB: " + abel2.noderMedNokMinne(32));
			System.out.println("Noder med minst 64 GB: " + abel2.noderMedNokMinne(64));
			System.out.println("Noder med minst 128 GB: " + abel2.noderMedNokMinne(128));
			System.out.println("Antall rack: " + abel2.anntalRack());
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
