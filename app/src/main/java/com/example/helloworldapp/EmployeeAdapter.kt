package com.example.helloworldapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class EmployeeAdapter(
    private val context: Context,
    private var employees: MutableList<Employee>,
    private val onDeleteClick: (Employee) -> Unit,
    private val onEditClick: (Employee) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = employees.size

    override fun getItem(position: Int): Any = employees[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.employee_item, parent, false)
        val employee = employees[position]

        val textViewEmployeeName: TextView = view.findViewById(R.id.textViewEmployeeName)
        val textViewEmployeePhone: TextView = view.findViewById(R.id.textViewEmployeePhone)
        val textViewEmployeeEmail: TextView = view.findViewById(R.id.textViewEmployeeEmail)
        val buttonDeleteEmployee: Button = view.findViewById(R.id.buttonDeleteEmployee)
        val buttonEditEmployee: Button = view.findViewById(R.id.buttonEditEmployee)

        textViewEmployeeName.text = employee.name
        textViewEmployeePhone.text = employee.phone
        textViewEmployeeEmail.text = employee.email

        buttonDeleteEmployee.setOnClickListener {
            onDeleteClick(employee)
        }

        buttonEditEmployee.setOnClickListener {
            onEditClick(employee)
        }

        return view
    }

    fun updateData(newEmployees: MutableList<Employee>) {
        employees = newEmployees
        notifyDataSetChanged()
    }
}
