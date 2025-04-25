# 4.1 
Pour le champ remark, destiné à accueillir un texte pouvant être plus long qu’une seule ligne,
quelle configuration particulière faut-il faire dans le fichier XML pour que son comportement
soit correct ? Nous pensons notamment à la possibilité de faire des retours à la ligne, d’activer
le correcteur orthographique et de permettre au champ de prendre la taille nécessaire

Il faut que le champ android:inputType soit textMultiLine pour pour qu'il fasse plusieurs lignes et textAutoCorrect pour le correcteur orthographique.

# 4.2
Pour afficher la date sélectionnée via le DatePicker nous pouvons utiliser un DateFormat
permettant par exemple d’afficher 12 juin 1996 à partir d’une instance de Date. Le formatage
des dates peut être relativement différent en fonction des langues, la traduction des mois par
exemple, mais également des habitudes régionales différentes : la même date en anglais
britannique serait 12th June 1996 et en anglais américain June 12, 1996. Comment peut-on
gérer cela au mieux ?

Avec DateFormat: dateFormatter = DateFormat.getDateInstance()

Ceci dépends des settings du téléphone et sera donc conforme au format que l'utilisateur a l'habitude de voir.

# 4.3
Veuillez choisir une question en fonction de votre choix d’implémentation :

b. Si vous avez utilisé le MaterialDatePicker2 de la librairie Material. Est-il possible de limiter
les dates sélectionnables dans le dialogue, en particulier pour une date de naissance il est
peu probable d’avoir une personne née il y a plus de 110 ans ou à une date dans le futur.
Comment pouvons-nous mettre cela en place ?

Avec CalendarConstraints (example tiré de la doc DatePicker):

```
val today = MaterialDatePicker.todayInUtcMilliseconds()
val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

calendar.timeInMillis = today
calendar[Calendar.MONTH] = Calendar.JANUARY
val janThisYear = calendar.timeInMillis

calendar.timeInMillis = today
calendar[Calendar.MONTH] = Calendar.DECEMBER
val decThisYear = calendar.timeInMillis

// Build constraints.
val constraintsBuilder =
   CalendarConstraints.Builder()
       .setStart(janThisYear)
       .setEnd(decThisYear)
```

Pour ne pas a voir de date de naissance dans le futur, on peut utiliser `setValidator(DateValidatorPointBackward.now())` et pour ne pas avoir de date qui est plus de 110 ans dans le passé, `setStart(Calendar.getInstance().apply { add(Calendar.YEAR, -110) }.timeInMillis)`

When building the DatePicker, we can call setCalendarConstraints(dateConstraints) to apply the constraints to the date picker.

# 4.4
Lors du remplissage des champs textuels, vous pouvez constater que le bouton « suivant »
présent sur le clavier virtuel permet de sauter automatiquement au prochain champ à saisir,
cf. Fig. 2. Est-ce possible de spécifier son propre ordre de remplissage du questionnaire ?
Arrivé sur le dernier champ, est-il possible de faire en sorte que ce bouton soit lié au bouton
de validation du questionnaire ?
Hint : Le champ remark, multilignes, peut provoquer des effets de bords en fonction du clavier
virtuel utilisé sur votre smartphone. Vous pouvez l’échanger avec le champ e-mail pour faciliter
vos recherches concernant la réponse à cette question

Avec android:nextFocusDown="@+id/..." on peut décider nous-même de l'ordre des sauts.

Pour lier le bouton à la validation du questionnaire, on peut faire de cette manière (sur l'input email au lieu du commentaire car le bouton suivant est remplacé par le bouton retour à la ligne sur un multiligne dans le clavier android du téléphone utilisé pour tester):
```kotlin
val lastEditText = binding.inputEmail
        lastEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        lastEditText.setOnEditorActionListener { textView: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleFormSubmission()
                return@setOnEditorActionListener true
            }
            false
        }
```

Dans la méthode onCreate de class MainActivity : AppCompatActivity()

# 4.5
Pour les deux Spinners (nationalité et secteur d’activité), comment peut-on faire en sorte que
le premier choix corresponde au choix null, affichant par exemple le label « Sélectionner » ?
Comment peut-on gérer cette valeur pour ne pas qu’elle soit confondue avec une réponse ?

On peut mettre une valeure dummy en premier élément de l'array de string qu'on passe au spinner. Par exemple pour le secteur: 

```xml
<string-array name="sectors">
    <item>@string/sectors_empty</item>
    <item>@string/agriculture</item>
    <item>@string/art</item>
    <item>@string/construction</item>
    <item>@string/management</item>
    <item>@string/law</item>
    <item>@string/teaching</item>
    <item>@string/tourism</item>
    <item>@string/industrie</item>
    <item>@string/ic</item>
    <item>@string/humansciences</item>
    <item>@string/health</item>
    <item>@string/sciences</item>
    <item>@string/social</item>
    <item>@string/sport</item>
    <item>@string/logistic</item>
</string-array>
```


La fonction pour submit le formulaire valide d'abord les inputs. On peut vérifier que l'input du spinner n'est pas l'élément à indice 0. S'il l'est, on envoie un Toast d'erreur, sinon on retourne true et le formulaire est submit normalement.
```kotlin
if(binding.dropdownNationality.selectedItemPosition==0){
        Toast.makeText(this, getString(R.string.error_nationality_empty), Toast.LENGTH_SHORT).show()
        false
} else if(binding.radioWorker.isChecked
    && binding.dropdownSector.selectedItemPosition==0){
    Toast.makeText(this, getString(R.string.error_sector_empty), Toast.LENGTH_SHORT).show()
    false
}else {
    true
}
```