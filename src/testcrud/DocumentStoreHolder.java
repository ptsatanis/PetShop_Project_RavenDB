/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcrud;
import net.ravendb.client.documents.DocumentStore;
/**
 *
 * @author Tsatanis Panagiotis and Maris Athanasios
 */
public class DocumentStoreHolder {
    private static DocumentStore store;
    
    public DocumentStoreHolder() {
        DocumentStoreHolder.store = null;
    }

    //Establish the communication between the client API and the RavenDB Server.
    private static DocumentStore createDocumentStore() {
        String serverURL = "http://localhost:8080";
        String databaseName = "Test";

        DocumentStore documentStore = new DocumentStore(new String[] { serverURL }, databaseName);

        documentStore.initialize();
        return documentStore;
    }

    public static DocumentStore getStore() {
        if (store == null) {
            store = createDocumentStore();
        }

        return store;
    }
}
