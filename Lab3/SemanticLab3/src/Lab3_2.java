import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;

public class Lab3_2 {

	public static void main(String[] args) {
		org.apache.log4j.Logger.getRootLogger().setLevel(
				org.apache.log4j.Level.OFF); // Removing warning log4j
		QueryExecution queryExecution = null;
		Dataset dataset = null;
		try {
			// Creating the TDB store
			String TDBDirectory = "MyDatabases/Dataset1";
			dataset = TDBFactory.createDataset(TDBDirectory);
			// Creating the default model
			Model model = ModelFactory.createDefaultModel();
			// Logging the start timer
			long startTimer = System.currentTimeMillis();
			// Loading the rdf file
			InputStream inputStream = FileManager.get().open("Monterey.rdf");
			dataset.begin(ReadWrite.WRITE);
			model.read(inputStream, null);
			dataset.commit();
			// Logging the end timer
			long endTimer = System.currentTimeMillis() - startTimer;
			float timeInSeconds = endTimer / 1000f;
			// Printing the time taken to load the rdf
			System.out.println("Time taken to load Moneterey.rdf is "
					+ timeInSeconds + " seconds");

			// Querying the class
			String string = "SELECT ?p ?o WHERE { <urn:monterey:#incident1> ?p ?o }";
			Query query = QueryFactory.create(string);
			queryExecution = QueryExecutionFactory.create(query, model);
			ResultSet result = queryExecution.execSelect();
			// Outputting the result in xml
			FileOutputStream outputStream = new FileOutputStream(
					"Lab3_2_ISuryanarayanan.xml");
			ResultSetFormatter.outputAsXML(outputStream, result);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			queryExecution.close();
			dataset.close();
		}

	}

}
