package it.polito.tdp.gosales;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.model.ArcoExt;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Products;
import it.polito.tdp.gosales.model.Retailers;
import it.polito.tdp.gosales.model.SimulationResult;
import it.polito.tdp.gosales.model.StatsConnessa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;
    
    @FXML
    private Button btnAnalizzaComponente;
    
    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;
    
    @FXML
    private ComboBox<Products> cmbProdotto;
    
    @FXML
    private ComboBox<Retailers> cmbRivenditore;
    
    @FXML
    private TextField txtN;
    
    @FXML
    private TextField txtQ;

    @FXML
    private TextField txtNProdotti;
    
    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextArea txtVertici;

    
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String nazione = this.cmbNazione.getValue();
    	if (nazione==null) {
    		this.txtResult.setText("Please select a country");
    		return;
    	}
    	
    	Integer anno = this.cmbAnno.getValue();
    	if (anno == null) {
    		this.txtResult.setText("Please select a year");
    		return;
    	}
    	
    	int nMin =0;
    	try {
    		nMin = Integer.parseInt( this.txtNProdotti.getText() );
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Invalid argument. N. Prodotti in Comune must be a nonnegative integer!");
    		return;
    	}
    	 if(nMin<0) {
    		 this.txtResult.setText("N. Prodotti in Comune must be a nonnegative integer.");
    	 }
    	
    	
    	//creazione grafp.
    	this.model.creaGrafo(nazione, anno, nMin);
    	
    	List<Retailers> vertici = this.model.getVertici();
    	Collections.sort(vertici);
    	this.txtResult.setText("Grafo creato, con " + vertici.size() + " vertici e " + this.model.getNArchi()+ " archi\n");
    	
    	//Stampa dei vertici
    	this.txtVertici.clear();
    	for(Retailers r : vertici) {
    		this.txtVertici.appendText(r + "\n");
    	}
    	
    	
    	//Stampa degli archi
    	List<ArcoExt> archi = this.model.getArchi();
    	Collections.sort(archi);
    	this.txtArchi.clear();
    	for(ArcoExt a : archi) {
    		this.txtArchi.appendText(a.getPeso() + ": " + a.getR1() + " <-> " + a.getR2() + "\n");
    	}
    	
    	//popolare la combo box del rivenditore
    	this.cmbRivenditore.getItems().clear();
    	this.cmbRivenditore.getItems().addAll(vertici);
    	
//    	//abilita i vari controlli della gui
    	this.cmbRivenditore.setDisable(false);
    	this.btnAnalizzaComponente.setDisable(false);
    	this.txtN.clear();
    	this.txtQ.clear();
    	this.cmbProdotto.setDisable(true);
    	this.txtN.setDisable(true);
    	this.txtQ.setDisable(true);
    	this.btnSimula.setDisable(true);
    }
    
    
    
    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	Retailers r = this.cmbRivenditore.getValue();
    	Integer anno = this.cmbAnno.getValue();
    	
    	if (r==null) {
    		this.txtResult.setText("Please select a retailer");
    		return;
    	}
    	if (anno == null) {
    		this.txtResult.setText("Please select a year");
    		return;
    	}
    	
    	//analizza la componente connessa
    	StatsConnessa result = this.model.analizzaComponente(r);
    	this.txtResult.appendText("\nLa componente connessa di " + r + " ha dimensione " + result.getRetailers().size()+ "\n");
    	this.txtResult.appendText("Il peso totale degli archi della componente connessa è " +  result.getPeso()+"\n");
    	
    	
    	//Popolare la combo box dei prodotti
    	this.cmbProdotto.getItems().clear();
    	this.cmbProdotto.getItems().addAll(this.model.getProductsRetailerYear(r, anno));
    	
    	//Abilitare i controlli della gui
    	this.cmbProdotto.setDisable(false);
    	this.txtN.setDisable(false);
    	this.txtQ.setDisable(false);
    	this.btnSimula.setDisable(false);
    	this.txtN.clear();
    	this.txtQ.clear();
    }
    
    
    
    
    @FXML
    void doSimulazione(ActionEvent event) {
    	Retailers r = this.cmbRivenditore.getValue();
    	if (r==null) {
    		this.txtResult.setText("Please select a retailer");
    		return;
    	}
    	
    	Products p = this.cmbProdotto.getValue();
    	if (p==null) {
    		this.txtResult.setText("Please select a product");
    		return;
    	}
    	
    	Integer anno = this.cmbAnno.getValue();
    	if (anno==null) {
    		this.txtResult.setText("Please select a year");
    		return;
    	}
    	
    	int q = 0;
    	int n = 0;
    	try {
    		q = Integer.parseInt(this.txtQ.getText());
    		n = Integer.parseInt(this.txtN.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("q and n must be integers");
    		return;
    	}
    	if (q < 0) {
    		this.txtResult.setText("q must be a nonnegative number");
    		return;
    	}
    	if (n <= 0) {
    		this.txtResult.setText("n must be a positive number");
    	}
    	
    	
    	SimulationResult res = this.model.eseguiSimulazione(p, q, n, r, anno);
    	this.txtResult.setText("Simulazione eseguita. Il risultato è\n");
    	this.txtResult.appendText(res + "\n");
    }
    
    
    

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	//popola Nazioni
    	List<String> nazioni = this.model.getNazioni();
    	Collections.sort(nazioni);
    	this.cmbNazione.getItems().addAll(nazioni);
    	this.cmbAnno.getItems().add(2015);
    	this.cmbAnno.getItems().add(2016);
    	this.cmbAnno.getItems().add(2017);
    	this.cmbAnno.getItems().add(2018);
    }
    
    

}
