# Android_ColorMemory


## Difference avec la correction du 19/03

### res/values/styles.xml:

Tous les boutons suivent le style ButtonStyle. Celui ci set par défaut une fonction sur le onClick     
  <item name="android:onClick">onButtonClick</item>
  
### java.com.tpillon.colormemory4.Logic.GameTask 

La task GameTask utilise simplement :
Button.setEnabled(boolean)

cette propriété permet de bloquer ou non l'appel à cette fonction lorsque l'utilisateur clique sur un bouton.

### java.com.tpillon.colormemory4.View.Activities.MainActivity 

cette classe gère l'interaction utilisateur
