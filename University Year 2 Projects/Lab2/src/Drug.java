public class Drug {
    String drugBankID;// the ID of the drug used for unique identification
    String genericName;// generic name for the given drug
    String SMILES;// Simplified Molecular Input Line Entry System for the drug
    String drugGroups;//groups the drug falls under

    Drug(String drugBankID, String genericName, String SMILES, String drugGroups){
        this.drugBankID = drugBankID;
        this.genericName = genericName;
        this.SMILES = SMILES;
        this.drugGroups = drugGroups;
    }
    /**
     * displays the drugs information
     */
    public void displayDrug(){
        System.out.println("Drug bank ID " + drugBankID + ", Generic name " + genericName + ", SMILES " + SMILES + ", Drug groups " + drugGroups);
    }
}

