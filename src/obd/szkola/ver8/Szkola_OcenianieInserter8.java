package obd.szkola.ver8;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Katarzyna Chojnowska 
 * 
 */

public class Szkola_OcenianieInserter8 {

	public static void main(String[] args) {
		
	String nazwSterownika = "oracle.jdbc.driver.OracleDriver";
	
		
		try {	
			Class<?> c= Class.forName(nazwSterownika);
			System.out.println("Pakiet: " + c.getPackage());
			System.out.println("Nazwa klasy: " + c.getName());
		}catch (Exception e) {	
			System.out.println("Wyj¹tek ze sterownika: " + e.getMessage());
			e.printStackTrace();
			 throw new RuntimeException();
		}		
		System.out.println("Sukces.Sterownik zaninstalowany");
		
		ocenianieInserter();
		
	}
		
		//Utworzenie tabel;Wprowadzania przygkladowych danych
		public static void ocenianieInserter() {	
			
			
			String url = "jdbc:oracle:thin:@//ora4.ii.pw.edu.pl:1521/pdb1.ii.pw.edu.pl";
			String uzytkownik = "kchojnow";
			String haslo = "kchojnow";
		
			int iduInput = 0; // przechowywanie id ucznia;
		int idnInput = 0; // przechowywanie id nauczyciela;
		int idpInput = 0; // przechowywanie id przedmiotu;
		int idoInput = 0; // przechowywanie id ocena;
		String rodzaj_ocenyInput = null; // przechowywanie rodzaju oceny C lub S;
		
		String input;
		Scanner keyboard = new Scanner(System.in);
		String createSQLCommand, inserter;	
	
		try (Connection polaczenie = DriverManager.getConnection(url, uzytkownik, haslo);
	  Statement statement = polaczenie.createStatement()) {
				System.out.println("Po³¹czony. " );
				
				Statement statement3 = null;
				
		
          createSQLCommand = "create table NAUCZYCIEL(idn int not null, nazwisko_nauczyciela char(30) not null, imie_nauczyciela char(20) not null)";
                inserter = "insert all " +
                        "into nauczyciel values (1, 'Steve', 'Jobs')" +
                        " into nauczyciel values (2, 'Smith', 'James')" +
                        " into nauczyciel values (3, 'Weisbarg', 'Helmud')" +
                        " into nauczyciel values (4, 'Musk', 'Elon')" +
                        " select * from dual";
                createTable(createSQLCommand, statement, "nauczyciel", inserter);
               
           createSQLCommand = "create table uczen(idu int not null, nazwisko_ucznia char(30) not null, imie_ucznia char(20) not null)";
                inserter = "insert all " +
                        "into uczen values (1, 'McDonald', 'Ann')" +
                        "into uczen values (2, 'Alleandro', 'Lorenz')" +
                        "into uczen values (3, 'Kowalski', 'Mariusz')" +
                        "into uczen values (4, 'Warszawska', 'Nadia')" +
                        "into uczen values (5, 'Santes', 'Pablo')" +
                        " select * from dual";
                createTable(createSQLCommand, statement, "uczen", inserter);
            
            createSQLCommand = "create table przedmiot (idp int not null, nazwa_przedmiotu char(20) not null)";
                inserter = "insert all " +
                        "into przedmiot values (1, 'matematyka')" +
                        "into przedmiot values (2, 'angielski')" +
                        "into przedmiot values (3, 'polski')" +
                        "into przedmiot values (4, 'informatyka')" +
                        "select * from dual";
                createTable(createSQLCommand, statement, "przedmiot", inserter);
                
            createSQLCommand = "create table ocena (ido int not null, wartoœæ_opisowa char(20) not null, wartoœæ_numeryczna float not null)";
                inserter = "insert all " +
                        "into ocena values (1, 'niedostateczny', 1)" +
                        "into ocena values (2, 'mierny', 2)" +
                        "into ocena values (3, 'dostateczny', 3)" + 
                        "into ocena values (4, 'dobry', 4)" +
                        "into ocena values (5, 'bardzo dobry', 5)" +  
                        "into ocena values (6, 'wzorowy', 6)" +
                        " select * from dual";
                createTable(createSQLCommand, statement, "ocena", inserter);
               
                createSQLCommand = "create table ocenianie (rodzaj_oceny char(1) not null, idn int not null, idu int not null, ido int not null, idp int not null)";
                createTable(createSQLCommand, statement, "ocenianie", "");
                
                
             // pobieranie danych od u¿ytkownika do tabeli OCENIANIE w BD 
				String selectQueryUczen = "SELECT idu FROM UCZEN WHERE idu=?";
				PreparedStatement psUczen = polaczenie.prepareStatement(selectQueryUczen);
				
				String selectQueryNauczyciel = "SELECT idn FROM Nauczyciel WHERE idn=?";
				PreparedStatement psNauczyciel = polaczenie.prepareStatement(selectQueryNauczyciel);
				
				String selectQueryPrzedmiot = "SELECT idp FROM Przedmiot WHERE idp=?";
				PreparedStatement psPrzedmiot = polaczenie.prepareStatement(selectQueryPrzedmiot);
				
				String selectQueryOcena = "SELECT ido FROM Ocena WHERE ido=?";
				PreparedStatement psOcena = polaczenie.prepareStatement(selectQueryOcena);
				
				String selectQueryRodzajOceny = "SELECT rodzaj_oceny FROM Ocenianie WHERE rodzaj_oceny=?";
				PreparedStatement psRodzajOceny = polaczenie.prepareStatement(selectQueryRodzajOceny);	
		
				System.out.println("Podaj id ucznia: ");

				iduInput = keyboard.nextInt();
				psUczen.setInt(1,iduInput);
				try (ResultSet rsUczen = psUczen.executeQuery()) {
					if (!rsUczen.next()) {
						System.out.println("Tabela UCZEN nie posiada recordu o id " + iduInput);
						System.out.println("Zacznij program od pocz¹tku. ");
						 return ;
					}
				}
			
				System.out.println("Podaj id nauczyciela: ");
				
				idnInput = keyboard.nextInt();
				psNauczyciel.setInt(1, idnInput);
				try (ResultSet rsNauczyciel = psNauczyciel.executeQuery()) {
					if (!rsNauczyciel.next()) {
						System.out.println("Tabela NAUCZYCIEL nie posiada recordu o id " + idnInput);
						System.out.println("Zacznij program od pocz¹tku. ");
						 return ;
					}
				}
				
				System.out.println("Podaj id przedmiotu: ");
				
				idpInput = keyboard.nextInt();
				psPrzedmiot.setInt(1, idpInput);
				try (ResultSet rsPrzedmiot = psPrzedmiot.executeQuery()) {
					if (!rsPrzedmiot.next()) {
						System.out.println("Tabela PRZEDMIOT nie posiada recordu o id " + idpInput);
						System.out.println("Zacznij program od pocz¹tku. ");
						 return ;
					}
				}
	
				System.out.println("Podaj id oceny:");
				idoInput = keyboard.nextInt();
				psOcena.setInt(1, idoInput);
				try (ResultSet rsOcena = psOcena.executeQuery()) {
					if (!rsOcena.next()) {
						System.out.println("Tabela OCENA nie posiada recordu o id " + idoInput);
						System.out.println("Zacznij program od pocz¹tku. ");
						 return ;
					}
				}

				System.out.println("Podaj symbol oceny: 'C' lub 'S'");
				input = keyboard.next();
				rodzaj_ocenyInput = input.toUpperCase();
				psRodzajOceny.setString(1, rodzaj_ocenyInput);
				// rodzaj_oceny = Character.toUpperCase(keyboard.next().charAt(0));
				while (!(rodzaj_ocenyInput.equals("C") || rodzaj_ocenyInput.equals("S"))){ 
				
					try (ResultSet rsRodzajOceny = psRodzajOceny.executeQuery()) {
						if (!rsRodzajOceny.next()) {
							System.out.println("Wpisz poprawn¹ ocenê: 'C' lub 'S' ");
							input = keyboard.next();
							rodzaj_ocenyInput = input.toUpperCase();
							psRodzajOceny.setString(1, rodzaj_ocenyInput);
						}
					}
				}	
				  
				System.out.println("Result from user input before validation:");
					int iduOutput = iduInput;
					int idnOutput = idnInput;
					int idpOutput = idnInput;
					int idoOutput = idoInput;
					String rodzaj_ocenyOutput = rodzaj_ocenyInput;
				
					System.out.println("iduInput: " + iduOutput);
					System.out.println("idnInput: " + idnOutput);
				    System.out.println("idpInput: " + idpOutput);
				    System.out.println("idoInput: " + idoOutput); 
				    System.out.println("idoInput: " + idoOutput);
				    System.out.println("rodzaj_ocenyInput: " + rodzaj_ocenyOutput);	  
			
					int idu = iduOutput;
					int idn = idnOutput;
					int idp = idnOutput;
					int ido = idoInput;
					String rodzaj_oceny = rodzaj_ocenyOutput;
					
					
					System.out.println();	
					System.out.println("Result from user input after validation:");
					System.out.println("idu: " + idu);
					
					System.out.println("idn: " + idn);
					System.out.println("idp: " + idp);
					System.out.println("ido: " + ido);
					System.out.println("rodzaj oceny: " + rodzaj_oceny);
					System.out.println();	

					System.out.println("========================================================");
					System.out.println("Wstawianie danych poprzez  PrepapredStatement.");
					String preparedQuery = "INSERT INTO ocenianie (idu, idn, idp, ido, rodzaj_oceny) VALUES (?,?,?,?,?)";
					PreparedStatement ps = polaczenie.prepareStatement(preparedQuery);
					ps.setInt(1, idu);
					ps.setInt(2, idn);
					ps.setInt(3, idp);
					ps.setInt(4, ido);
					ps.setString(5, rodzaj_oceny);

					int count = ps.executeUpdate();
					System.out.println("W porz¹dku mamy twoje dane z Prepared Statement Type!");
					System.out.println(count + " dodany 1 rz¹d.");    
									  
				
				System.out.println("==========================================================");
				System.out.println(); 
				
				System.out.println("To jest uaktualnienie tabeli ocenianie Ocenianie ordered by IDU:");
				System.out.println();

				// create object to print out table OCENIANIE in console:
				statement3 = polaczenie.createStatement();
				// creating object String to select data from table OCENIANIE
				String resultStatement = "SELECT * FROM ocenianie ORDER BY idu";

				// sent resultStatement to DB Szkola
				ResultSet result = statement3.executeQuery(resultStatement);

				System.out.println("OCENIANIE TABELA:");
				System.out.println("------------------");
				while (result.next()) {

					System.out.printf("%2d %1d %1d %1.1f %1s\n", result.getInt("idu"), result.getInt("idn"),
							result.getInt("idp"), result.getFloat("ido"), result.getString("rodzaj_oceny"));
				}
				System.out.println();
  		
				polaczenie.close();	

			} catch (SQLException e) {
			    System.err.println("B³¹d:");
				e.printStackTrace();
			}
	        finally {        
           	    keyboard.close();
                System.out.println("Finally");
	        }		

	}	
public static void createTable(String createSQLCommand, Statement statement, String tableName, String inserter) throws SQLException {
    String sql;
    sql = "declare begin " +
            "execute immediate '" + createSQLCommand + "' ;  " +
            "exception when others then if SQLCODE = -955 then null; " +
            "else raise; " +
            "end if; " +
            "end;";
    statement.execute(sql);  
    if (!Objects.equals(inserter, "") && inserter != null) {
        statement.execute(inserter);
    }
  }


}
	

