package ch.heigvd.iict.daa.template

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.databinding.ActivityMainBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDatePicker()
        initSpinners()
        setupListeners()

        // Masquer initialement les sections spécifiques pour Étudiant et Employé
        binding.groupStudent.visibility = View.GONE
        binding.groupWorker.visibility = View.GONE

        // Initialiser la vue avec un exemple d'objet (ex. Worker ou Student)
        //initializeWithExampleData(Person.exampleWorker)  // Changez pour exampleStudent si besoin
    }

    /**
     * Initialise le formulaire avec un exemple de données.
     */
    private fun initializeWithExampleData(person: Person) {
        with(binding) {
            inputName.setText(person.firstName)
            inputSurname.setText(person.name)
            inputBirthdate.setText(Person.dateFormatter.format(person.birthDay.time))
            dropdownNationality.setSelection(getSpinnerPosition(dropdownNationality, person.nationality))
            inputEmail.setText(person.email)
            inputComments.setText(person.remark)

            when (person) {
                is Student -> {
                    radioStudent.isChecked = true
                    inputSchool.setText(person.university)
                    inputGraduationYear.setText(person.graduationYear.toString())
                    groupStudent.visibility = View.VISIBLE
                    groupWorker.visibility = View.GONE
                }
                is Worker -> {
                    radioWorker.isChecked = true
                    inputCompany.setText(person.company)
                    dropdownSector.setSelection(getSpinnerPosition(dropdownSector, person.sector))
                    inputExperience.setText(person.experienceYear.toString())
                    groupWorker.visibility = View.VISIBLE
                    groupStudent.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Initialise le DatePicker avec des contraintes (pas de dates futures).
     */
    private fun initDatePicker() {
        val dateConstraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
            .setStart(Calendar.getInstance().apply { add(Calendar.YEAR, -110) }.timeInMillis)
            .build()

        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.main_base_birthdate_dialog_title))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(dateConstraints)
            .build()

        // Définir l'action après sélection de la date
        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
            binding.inputBirthdate.setText(Person.dateFormatter.format(Date(dateInMillis)))
        }
    }

    /**
     * Initialise les spinners pour la nationalité et le secteur.
     */
    private fun initSpinners() {
        val nationalities = resources.getStringArray(R.array.nationalities)
        binding.dropdownNationality.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nationalities)

        val sectors = resources.getStringArray(R.array.sectors)
        binding.dropdownSector.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sectors)
    }

    /**
     * Configure les écouteurs de clics pour les composants de l'interface.
     */
    private fun setupListeners() {
        binding.iconCake.setOnClickListener { datePicker.show(supportFragmentManager, "DatePicker") }
        binding.inputBirthdate.setOnClickListener { datePicker.show(supportFragmentManager, "DatePicker") }

        binding.groupOccupation.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_student -> {
                    binding.groupStudent.visibility = View.VISIBLE
                    binding.groupWorker.visibility = View.GONE
                }
                R.id.radio_worker -> {
                    binding.groupStudent.visibility = View.GONE
                    binding.groupWorker.visibility = View.VISIBLE
                }
                else -> {
                    binding.groupStudent.visibility = View.GONE
                    binding.groupWorker.visibility = View.GONE
                }
            }
        }

        binding.buttonReset.setOnClickListener { resetForm() }
        binding.buttonSubmit.setOnClickListener { handleFormSubmission() }
    }

    /**
     * Gère la soumission du formulaire : crée un Student ou Worker et l'affiche dans les logs.
     */
    private fun handleFormSubmission() {
        if (!validateInputs()) return

        val person = if (binding.radioStudent.isChecked) {
            createStudentFromForm()
        } else {
            createWorkerFromForm()
        }
        Log.i("MainActivity", person.toString())
        Toast.makeText(this, getString(R.string.success_save), Toast.LENGTH_LONG).show()
    }

    /**
     * Crée un objet Student à partir des champs du formulaire.
     */
    private fun createStudentFromForm(): Student {
        return Student(
            name = binding.inputSurname.text.toString(),
            firstName = binding.inputName.text.toString(),
            birthDay = getCalendarFromForm(),
            nationality = binding.dropdownNationality.selectedItem.toString(),
            university = binding.inputSchool.text.toString(),
            graduationYear = binding.inputGraduationYear.text.toString().toIntOrNull() ?: 0,
            email = binding.inputEmail.text.toString(),
            remark = binding.inputComments.text.toString()
        )
    }

    /**
     * Crée un objet Worker à partir des champs du formulaire.
     */
    private fun createWorkerFromForm(): Worker {
        return Worker(
            name = binding.inputSurname.text.toString(),
            firstName = binding.inputName.text.toString(),
            birthDay = getCalendarFromForm(),
            nationality = binding.dropdownNationality.selectedItem.toString(),
            company = binding.inputCompany.text.toString(),
            sector = binding.dropdownSector.selectedItem.toString(),
            experienceYear = binding.inputExperience.text.toString().toIntOrNull() ?: 0,
            email = binding.inputEmail.text.toString(),
            remark = binding.inputComments.text.toString()
        )
    }

    /**
     * Valide les entrées du formulaire et affiche des messages d'erreur pour les champs vides.
     */
    private fun validateInputs(): Boolean {
        if (binding.groupOccupation.checkedRadioButtonId == -1) {
            Toast.makeText(this, getString(R.string.error_occupation_empty), Toast.LENGTH_SHORT).show()
            return false
        }

        val emptyField = when {
            binding.inputName.text.isBlank() -> binding.inputName
            binding.inputSurname.text.isBlank() -> binding.inputSurname
            binding.inputBirthdate.text.isBlank() -> binding.inputBirthdate
            binding.inputEmail.text.isBlank() -> binding.inputEmail
            binding.radioStudent.isChecked && binding.inputSchool.text.isBlank() -> binding.inputSchool
            binding.radioStudent.isChecked && binding.inputGraduationYear.text.isBlank() -> binding.inputGraduationYear
            binding.radioWorker.isChecked && binding.inputCompany.text.isBlank() -> binding.inputCompany
            binding.radioWorker.isChecked && binding.inputExperience.text.isBlank() -> binding.inputExperience
            else -> null
        }

        return if (emptyField != null) {
            emptyField.error = getString(R.string.error_field_empty)
            false
        } else if(binding.dropdownNationality.selectedItemPosition==0){
            Toast.makeText(this, getString(R.string.error_nationality_empty), Toast.LENGTH_SHORT).show()
            false
        } else if(binding.radioWorker.isChecked
            && binding.dropdownSector.selectedItemPosition==0){
            Toast.makeText(this, getString(R.string.error_sector_empty), Toast.LENGTH_SHORT).show()
            false
        }else {
            true
        }
    }

    /**
     * Récupère une instance de Calendar à partir de la date du formulaire.
     */
    private fun getCalendarFromForm(): Calendar {
        return Calendar.getInstance().apply {
            time = Person.dateFormatter.parse(binding.inputBirthdate.text.toString()) ?: Date()
        }
    }

    /**
     * Réinitialise le formulaire en effaçant le contenu des champs.
     */
    private fun resetForm() {
        binding.groupOccupation.clearCheck()
        binding.inputName.text.clear()
        binding.inputSurname.text.clear()
        binding.inputBirthdate.text.clear()
        binding.dropdownNationality.setSelection(0)
        binding.inputEmail.text.clear()
        binding.inputComments.text.clear()

        // Champs spécifiques pour Étudiant
        binding.inputSchool.text.clear()
        binding.inputGraduationYear.text.clear()

        // Champs spécifiques pour Employé
        binding.inputCompany.text.clear()
        binding.dropdownSector.setSelection(0)
        binding.inputExperience.text.clear()

        // Masquer les sections spécifiques
        binding.groupStudent.visibility = View.GONE
        binding.groupWorker.visibility = View.GONE
    }

    /**
     * Trouve la position d'un élément dans un Spinner.
     */
    private fun getSpinnerPosition(spinner: Spinner, value: String): Int {
        val adapter = spinner.adapter as ArrayAdapter<CharSequence>
        return adapter.getPosition(value)
    }
}
