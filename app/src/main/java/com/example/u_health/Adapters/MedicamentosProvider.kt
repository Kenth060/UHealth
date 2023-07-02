package com.example.u_health.Adapters

import com.example.u_health.model.Citas
import com.example.u_health.model.Medicamentos


class MedicamentosProvider
{
    companion object{

        val Analgesicos= listOf<String>("Aspirina","Celecoxib","Codeina", "Dexketoprofeno", "Diclofenaco", "Hidrocodona", "Ibuprofeno", "Indometacina", "Ketorolaco", "Meloxicam", "Meperidina", "Morfina", "Naproxeno", "Nimesulida", "Oxicodona", "Paracetamol(Acetominofen)", "Piroxicam", "Propoxifeno", "Tenoxicam", "Tramadol")
        val Prueba= listOf<String>("A","B","C")
        val Antibioticos= listOf<String>("Amoxicilina","Azitromicina","Cefalexina", "Ceftazidima", "Ceftriaxona", "Cefuroxina", "Ciprofloxacino", "Claritromicina", "Climandicina", "Doxiciclina", "Ertapenem", "Gentamicina", "Imipenem", "Levofloxacino", "Linezolid", "Meropenem", "Metrodinazol", "Tigeciclina", "Trimetropina-sulfametoxazol", "Vancomicina")
        val Antidepresivos= listOf<String>("Amitriptilina", "Bupropion", "Citalopram", "Desvelafaxina","Duloxetina","Escitalopram","Fluoxetina", "Fluvoxamina","Levomilnacipran","Mirtazapina","Nortriptilina", "Paroxetina","Reboxetina","Sertralina","Trazonoda", "Venlafaxina","Vilazodona","Vortoxiotina")
        val Antiflamatorios= listOf<String>("Aceclofenaco","Celecoxib","Dexketoprofeno","Diclofenaco", "Etodolaco","Flurbiprofeno","Ibuprofeno","Indometacina", "Ketoprofeno","Ketorolaco","Lornoxicam","Meloxicam", "Nabumetona","Naproxeno","Nimesulida","Oxaprozina","Piroxicam","Sulindaco","Tenoxicam","Tolmetina")
        val MedDiabetes=listOf<String>("Canaglifozina","Dulaglutida","Empagliflozina","Exenatida", "Glibenclamida","Insulina detemir","Insulina glargina","Insulina glulisina","Insulina lispro","Linagliptina","Metformina", "Pioglitazona","Repaglinida","Saxagliptina","Sitagliptina", "Vildagliptina")
        val MediTosGripe=listOf<String>("Acetaminofén","Acetaminofén-clorfenamina-fenilefrina","Acetaminofén-dextrometorfano-clorfenamina", "Ambroxol","Bromhexina","Clorfenamina","Clorfeniramina", "Codeina","Dextrometorfano","Dextrometorfano-guaifenesina-bromhexina", "Fenilefrina","Guaifenesina","Guaifenesina-dextrometorfano", "Ibuprofeno","Loratadina","Loratadina-Pseudoefedrina", "Paracetamol-dextrometorfano-clorfenamina-fenilefrina", "Paracetamol-dextrometorfano-guaifenesina","Pseudoefedrina", "Pseudoefedrina-dextrometorfano-guaifenesina")
        var Recordatorios_Meds = mutableListOf<Medicamentos>(Medicamentos("Ibuprofeno","2 veces al dia","3:00","30"))
        var Recordatorios_Citas = mutableListOf<Citas>(Citas("Ortopedia","Nahum Gutierrez","30/06/2023","15:00",""), Citas("Medicina General","Joseph Rivas","30/06/2023","15:00",""))

    }
}