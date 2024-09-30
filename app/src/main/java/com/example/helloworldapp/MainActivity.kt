package com.example.helloworldapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var employeeDao: EmployeeDao
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "employee-database"
        ).allowMainThreadQueries().build()

        employeeDao = db.employeeDao()

        val employees = employeeDao.getAll()
        Log.d("MainActivity", "Initial employees: $employees")

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val listViewEmployees: ListView = findViewById(R.id.listViewEmployees)

        adapter = EmployeeAdapter(this, employees.toMutableList(), { employee ->
            employeeDao.delete(employee)
            adapter.updateData(employeeDao.getAll().toMutableList())
        }, { employee ->
            showEditDialog(employee)
        })
        listViewEmployees.adapter = adapter

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                val employee = Employee(name = name, phone = phone, email = email)
                employeeDao.insert(employee)
                Log.d("MainActivity", "Employee added: $employee")
                adapter.updateData(employeeDao.getAll().toMutableList())
                editTextName.text.clear()
                editTextPhone.text.clear()
                editTextEmail.text.clear()
            }
        }
    }

    private fun showEditDialog(employee: Employee) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_employee, null)
        val editTextName: EditText = dialogView.findViewById(R.id.editTextEditName)
        val editTextPhone: EditText = dialogView.findViewById(R.id.editTextEditPhone)
        val editTextEmail: EditText = dialogView.findViewById(R.id.editTextEditEmail)

        editTextName.setText(employee.name)
        editTextPhone.setText(employee.phone)
        editTextEmail.setText(employee.email)

        AlertDialog.Builder(this)
            .setTitle("Edit Employee")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newName = editTextName.text.toString()
                val newPhone = editTextPhone.text.toString()
                val newEmail = editTextEmail.text.toString()
                if (newName.isNotEmpty() && newPhone.isNotEmpty() && newEmail.isNotEmpty()) {
                    employee.name = newName
                    employee.phone = newPhone
                    employee.email = newEmail
                    employeeDao.update(employee)
                    adapter.updateData(employeeDao.getAll().toMutableList())
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}
