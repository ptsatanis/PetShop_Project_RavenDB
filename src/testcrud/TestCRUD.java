/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testcrud;

import java.util.*;
import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;
import net.ravendb.client.documents.operations.GetStatisticsOperation;
import net.ravendb.client.documents.operations.DatabaseStatistics;


/**
 *
 * @author Tsatanis Panagiotis and Maris Athanasios
 */

public class TestCRUD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner Sc = new Scanner(System.in);
        String choice;
        
        //Initialize connection with the database
        DocumentStore DocumentStore = DocumentStoreHolder.getStore();
        
        try (IDocumentSession session = DocumentStore.openSession("Test")) {
            CRUDOperations op = new CRUDOperations();
            
            //Count number of documents so that correct ids are assigned to each document.
            DatabaseStatistics statistics = DocumentStore.maintenance().send(new GetStatisticsOperation());
            long DBSize = statistics.getCountOfDocuments();
            
            while( Sc.hasNext() ) {
                //choice must be one of: -ip, -gp, -up, -dp, -ic, -gc, -uc, -dc
                choice = Sc.next();
                if( choice.equals("-ip") ) {
                    //Insert pet's info
                    DBSize++;
                    int age = Sc.nextInt();
                    int weight = Sc.nextInt();
                    int price = Sc.nextInt();
                    String breed = Sc.next();
                    String gender = Sc.next();
                    
                    Pet newp = new Pet(DBSize, age, weight, price, breed, gender);
                    
                    if( op.createDocument(newp) )
                        System.out.println(newp.getDocID() + " created!");
                    else
                        System.out.println(newp.getDocID() + " not created!");
                }
                else if( choice.equals("-dp") ) {
                    
                    if( Sc.hasNext() ) {
                        //Specify which pet to delete
                        String docID = Sc.next();
                        
                        if( op.deleteDoc(docID) )
                            System.out.println("Document with name " + docID + " deleted!");
                        else
                            System.out.println("Document with name " + docID + " not deleted!");
                    }
                }
                else if( choice.equals("-up") ) {
                    //Specify pet and insert new info
                    
                    String docID = Sc.next();
                    int age = Sc.nextInt();
                    int weight = Sc.nextInt();
                    int price = Sc.nextInt();
                    String breed = Sc.next();
                    String gender = Sc.next();
                    
                    Pet newp = new Pet(DBSize, age, weight, price, breed, gender, docID);
                    
                    if( op.updateDoc(docID, newp, false) )
                        System.out.println("Update of " + docID + " done!");
                    else
                        System.out.println("Update of " + docID + " failed!");
                    
                    Pet p = op.readDoc(docID);
                    p.printPet();
                    
                }
                else if( choice.equals("-gp") ) {
                    Pet res = null;
                    
                    if( Sc.hasNext() ) {
                        //Specify pet.
                        String docID = Sc.next();
                        res = op.readDoc(docID);
                    }
                    if( res != null )
                        res.printPet();
                    else
                        System.out.println("Document not found");
                }
                else if( choice.equals("-ic") ) {
                    //Insert customer's info and orders.
                    DBSize++;
                    String fname = Sc.next();
                    String lname = Sc.next();
                    String phone = Sc.next();
                    String line = Sc.nextLine();
                    String[] array = line.split(" ");
                    String[] orders = new String[array.length - 1];
                    
                    //Cut the null string at the start of the array.
                    for(int i = 0; i < orders.length; i++) {
                        orders[i] = array[i + 1];
                        System.out.println(orders[i]);
                    }
                    
                    Customer c = new Customer(DBSize, fname, lname, phone, orders);
                    
                    if( op.createCustomer(c) )
                        System.out.println(c.getDocID() + " created!");
                    else
                        System.out.println(c.getDocID() + " not created!");
                }
                else if( choice.equals("-gc") ) {
                    Customer res = null;
                    
                    if( Sc.hasNext() ) {
                        //Specify customer.
                        String docID = Sc.next();
                        res = op.readCustomer(docID);
                    }
                    if( res != null )
                        res.printCustomer();
                    else
                        System.out.println("Document not found");
                }
                else if( choice.equals("-uc") ) {
                    /*Specify customer, enter new info,
                    specify whether old order history should be kept
                    (in case the customer cancelled his order),and enter new orders*/
                    String docID = Sc.next();
                    String fname = Sc.next();
                    String lname = Sc.next();
                    String phone = Sc.next();
                    String keepOldOrders = Sc.next();
                    String line = Sc.nextLine();
                    String[] array = line.split(" ");
                    
                    
                    String[] orders = new String[array.length - 1];
                    
                    //Cut the null string at the start of the array.
                    for(int i = 0; i < orders.length; i++)
                        orders[i] = array[i + 1];
                    
                    Customer newc = new Customer(DBSize, fname, lname, phone, orders);
                    
                    if( op.updateCustomer(docID, newc, keepOldOrders.equalsIgnoreCase("true")) )
                        System.out.println("Update of " + docID + " done!");
                    else
                        System.out.println("Update of " + docID + " failed!");
                }
                else if( choice.equals("-dc") ) {
                    if( Sc.hasNext() ) {
                        //Specify customer.
                        String docID = Sc.next();
                        
                        if( op.deleteCustomer(docID) )
                            System.out.println("Document with name " + docID + " deleted!");
                        else
                            System.out.println("Document with name " + docID + " not deleted!");
                    }
                }
                else if( choice.equals("-query") ) {
                    if( Sc.hasNext() ) {
                        /*Specify which query to run and 
                        provide additional input if needed.*/
                        String query = Sc.next();
                        UsefulQueries q = new UsefulQueries();
                        q.querySelection(query, Sc);
                    }
                }
                else break;
            }
        }
        catch(Exception ex) {
            System.err.println("Initial connection failed\n" + ex.getMessage());
        }
        finally {
            //Close the session and the connection.
            Sc.close();
            DocumentStore.close();
        }
        
    }
    
}
