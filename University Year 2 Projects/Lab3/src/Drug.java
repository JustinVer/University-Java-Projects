public class Drug {
    String drugBankID;// the ID of the drug used for unique identification
    String genericName;// generic name for the given drug
    String SMILES;// Simplified Molecular Input Line Entry System for the drug
    String drugGroups;//groups the drug falls under
    String url;//url for drug
    Double score;//score of the drug


    Drug(String drugBankID, String genericName, String SMILES, String drugGroups, String url, Double score){
        this.drugBankID = drugBankID;
        this.genericName = genericName;
        this.SMILES = SMILES;
        this.drugGroups = drugGroups;
        this.url = url;
        this.score = score;
    }

    public void displayDrug(){
        System.out.println(drugBankID + " " + genericName + " " + SMILES + " " + drugGroups + " " + url + " " + score);
    }
}

