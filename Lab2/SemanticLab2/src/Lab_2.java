import java.io.FileWriter;
import java.io.IOException;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;

public class Lab_2 {

	public static void main(String[] args) {
		org.apache.log4j.Logger.getRootLogger().setLevel(
				org.apache.log4j.Level.OFF);

		// Stanley Kubrick's Data
		String StanleyKubrickURI = "http://utdallas/semclass#StanleyKubrick";
		String firstName = "Stanley Kubrick";
		String skDOB = "7-26-1928";
		String skRole = "Director";

		// Dr StangeLove Movie Data
		String DrStrangeLoveURI = "http://utdallas/semclass#DrStrangeLove";
		String DrStrangeLove = "Dr. Strange Love";
		String slYear = "1964";

		// A Clockwork Orange Movie Data
		String AClockworkOrangeURI = "http://utdallas/semclass#AClockworkOrange";
		String AClockworkOrange = "A Clockwork Orange";
		String coYear = "1971";

		// Red Alert Book Data
		String RedAlertBookURI = "http://utdallas/semclass#RedAlertBook";
		String RedAlert = "Red Alert Book";
		String raPYear = "1958";
		String raGenre = "Nuclear warfare";

		// A Clockwork Orange Book Data
		String AClockworkOrangeBookURI = "http://utdallas/semclass#AClockworkOrangeBook";
		String COBook = "A Clockwork Orange Book";
		String coPYear = "1962";
		String coGenre = "Science Fiction";

		// Peter George Author Data
		String PeterGeorgeURI = "http://utdallas/semclass#PeterGeorge";
		String PGName = "Peter George";
		String pgDOB = "3-24-1924";
		String pgRole = "Author";

		// Anthony Burgess Author Data
		String AnthonyBurgessURI = "http://utdallas/semclass#AnthonyBurgess";
		String ABName = "Anthony Burgess";
		String abDOB = "2-25-1917";
		String abRole = "Author";
		String directory = "MyDatabases/Dataset1";

		// Dataset creation
		Dataset dataset = TDBFactory.createDataset(directory);

		// Model creation
		Model model = dataset.getDefaultModel();
		dataset.begin(ReadWrite.WRITE);

		// Parent Resource Creation
		Resource personResource = model
				.createResource("http://utdallas/semclass#Person");
		Resource bookResource = model
				.createResource("http://utdallas/semclass#Book");
		Resource movieResource = model
				.createResource("http://utdallas/semclass#movie");
		Property DIRECTOR = model
				.createProperty("http://utdallas/semclass#director");
		Property AUTHOR = model
				.createProperty("http://utdallas/semclass#author");
		Property BASED_ON = model
				.createProperty("http://utdallas/semclass#basedOn");

		// Subclass Resource Creation
		Resource stanleyKendrickResource = model
				.createResource(StanleyKubrickURI);
		Resource drStrangeLoveResource = model.createResource(DrStrangeLoveURI);
		Resource aClockworkOrangeResource = model
				.createResource(AClockworkOrangeURI);
		Resource redAlertBookResource = model.createResource(RedAlertBookURI);
		Resource aClockworkOrangeBookResource = model
				.createResource(AClockworkOrangeBookURI);
		Resource peterGeorgeResource = model.createResource(PeterGeorgeURI);
		Resource anthonyBurgessResource = model
				.createResource(AnthonyBurgessURI);

		// Person properties added
		stanleyKendrickResource.addProperty(VCARD.FN, firstName)
				.addProperty(VCARD.BDAY, skDOB).addProperty(VCARD.ROLE, skRole)
				.addProperty(RDF.type, personResource);
		peterGeorgeResource.addProperty(VCARD.BDAY, pgDOB)
				.addProperty(VCARD.ROLE, pgRole)
				.addProperty(RDF.type, personResource)
				.addProperty(VCARD.FN, PGName);
		anthonyBurgessResource.addProperty(VCARD.BDAY, abDOB)
				.addProperty(VCARD.ROLE, abRole)
				.addProperty(RDF.type, personResource)
				.addProperty(VCARD.FN, ABName);

		// Movie Properties added
		drStrangeLoveResource.addProperty(DC.title, DrStrangeLove)
				.addProperty(DC.date, slYear)
				.addProperty(RDF.type, movieResource);
		aClockworkOrangeResource.addProperty(DC.title, AClockworkOrange)
				.addProperty(DC.date, coYear)
				.addProperty(RDF.type, movieResource);

		// Book Properties added
		redAlertBookResource.addProperty(DC.date, raPYear)
				.addProperty(DC.type, raGenre)
				.addProperty(RDF.type, bookResource)
				.addProperty(DC.title, RedAlert);
		aClockworkOrangeBookResource.addProperty(DC.date, coPYear)
				.addProperty(DC.type, coGenre)
				.addProperty(RDF.type, bookResource)
				.addProperty(DC.title, COBook);

		// Relationship between Movie and author and Book
		drStrangeLoveResource.addProperty(DIRECTOR, stanleyKendrickResource)
				.addProperty(BASED_ON, redAlertBookResource);
		aClockworkOrangeResource.addProperty(DIRECTOR, stanleyKendrickResource)
				.addProperty(BASED_ON, aClockworkOrangeBookResource);

		// Relationship between Book and Author
		redAlertBookResource.addProperty(AUTHOR, peterGeorgeResource);
		aClockworkOrangeBookResource
				.addProperty(AUTHOR, anthonyBurgessResource);

		FileWriter xmlStream, n3Stream;
		try {
			xmlStream = new FileWriter("Lab2_3_iSuryanarayanan.xml");
			n3Stream = new FileWriter("Lab2_3_iSuryanarayanan.n3");

			// Write the triples in XML, N-TRIPLE and N3 Format
			model.write(xmlStream, "RDF/XML-ABBREV");
			model.write(n3Stream, "N3");

			// End dataset
			dataset.end();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
