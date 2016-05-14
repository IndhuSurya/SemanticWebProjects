/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0

 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.*;

public class Lab1_4d extends Object {
	public static void main(String args[]) {

		
		String personURI = "http://utdallas/semclass#KevenAtes";
		String fName = "Dr. Keven  L. Ates ";
		String bDay = "1901-01-01 ";
		String eMail = "atescomp@utdallas.edu";
		String title = "Senior Lecturer ";
		String directory = "MyDatabases/Dataset1";

/* Log4j Initialization */
		org.apache.log4j.Logger.getRootLogger().setLevel(
				org.apache.log4j.Level.OFF);
/* Creation of Default Model to store vcard properties */
		Model model = ModelFactory.createDefaultModel();
		model.createResource(personURI)
				.addProperty(VCARD.TITLE, title).addProperty(VCARD.BDAY, bDay)
				.addProperty(VCARD.EMAIL, eMail)
				.addProperty(VCARD.FN, fName);
		try {
			model.write(new FileWriter("Lab1_2_iSuryanarayanan.xml", true),
					"RDF/XML");
			model.write(new FileWriter("Lab1_2_iSuryanarayanan.ntp", true),
					"N-TRIPLES");
			model.write(new FileWriter("Lab1_2_iSuryanarayanan.n3", true), "N3");
			
/* Making a TDB Backed Dataset */
			Dataset dataset = TDBFactory.createDataset(directory);
			dataset.begin(ReadWrite.WRITE);
/* Getting the model inside the transaction */
			Model modelTDB = dataset.getNamedModel("namedModel");
			InputStream inFile = FileManager.get().open(
					"indhumadhi_FOAFFriends.rdf");
			modelTDB.add(model);
			modelTDB.read(inFile, "defaultNameSpace");
			modelTDB.write(System.out);

			modelTDB.write(new FileWriter("Lab1_4_iSuryanarayanan.xml", true),
					"RDF/XML");
			modelTDB.write(new FileWriter("Lab1_4_iSuryanarayanan.ntp", true),
					"N-TRIPLES");
			modelTDB.write(new FileWriter("Lab1_4_iSuryanarayanan.n3", true),
					"N3");
			dataset.end();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}