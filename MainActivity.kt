package com.example.simpleexpense_tracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class Expense(val amount: Double, val category: String)

class MainActivity : AppCompatActivity() {

    private val expenses = mutableListOf<Expense>()
    private lateinit var expenseAmountInput: EditText
    private lateinit var expenseCategoryInput: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var viewSummaryButton: Button
    private lateinit var summaryListView: ListView
    private lateinit var summaryText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseAmountInput = findViewById(R.id.expenseAmount)
        expenseCategoryInput = findViewById(R.id.expenseCategory)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        viewSummaryButton = findViewById(R.id.viewSummaryButton)
        summaryListView = findViewById(R.id.summaryListView)
        summaryText = findViewById(R.id.summaryText)

        addExpenseButton.setOnClickListener { addExpense() }
        viewSummaryButton.setOnClickListener { viewSummary() }
    }

    private fun addExpense() {
        val amountText = expenseAmountInput.text.toString()
        val category = expenseCategoryInput.text.toString()

        val amount = amountText.toDoubleOrNull()
        if (amount == null || category.isEmpty()) {
            Toast.makeText(this, "Please enter a valid amount and category", Toast.LENGTH_SHORT).show()
            return
        }

        expenses.add(Expense(amount, category))
        Toast.makeText(this, "Expense added: $$amount in $category", Toast.LENGTH_SHORT).show()

        expenseAmountInput.text.clear()
        expenseCategoryInput.text.clear()
    }

    private fun viewSummary() {
        if (expenses.isEmpty()) {
            Toast.makeText(this, "No expenses recorded.", Toast.LENGTH_SHORT).show()
            return
        }

        val categoryTotals = expenses.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val summaryList = categoryTotals.map { (category, total) -> "$category: $$total" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, summaryList)
        summaryListView.adapter = adapter
        summaryText.visibility = TextView.VISIBLE
        summaryListView.visibility = ListView.VISIBLE
    }
}
