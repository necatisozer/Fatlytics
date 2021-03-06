package com.fatlytics.app.ui.main.diary

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.fatlytics.app.R
import com.fatlytics.app.data.source.api.FoodEntriesModel
import com.fatlytics.app.databinding.DiaryFragmentBinding
import com.fatlytics.app.extension.onSingleClick
import com.fatlytics.app.ui.addfood.AddFoodActivity
import com.fatlytics.app.ui.base.BaseViewModelFragment
import com.fatlytics.app.ui.main.home.CalorieFormatter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.ArrayList

class DiaryFragment : BaseViewModelFragment<DiaryViewModel, DiaryFragmentBinding>() {
    override val layoutRes = R.layout.diary_fragment
    override val viewModelClass = DiaryViewModel::class.java

    private val breakfastFoodEntryAdapter: FoodEntryAdapter by lazy {
        FoodEntryAdapter().apply {
            longClickListener = { deleteFoodEntry(it.food_entry_id?.toLongOrNull()) }
        }
    }
    private val lunchFoodEntryAdapter: FoodEntryAdapter by lazy {
        FoodEntryAdapter().apply {
            longClickListener = { deleteFoodEntry(it.food_entry_id?.toLongOrNull()) }
        }
    }
    private val dinnerFoodEntryAdapter: FoodEntryAdapter by lazy {
        FoodEntryAdapter().apply {
            longClickListener = { deleteFoodEntry(it.food_entry_id?.toLongOrNull()) }
        }
    }
    private val otherFoodEntryAdapter: FoodEntryAdapter by lazy {
        FoodEntryAdapter().apply {
            longClickListener = { deleteFoodEntry(it.food_entry_id?.toLongOrNull()) }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        initChart()

        binding.breakfastRecycler.apply {
            adapter = breakfastFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.lunchRecycler.apply {
            adapter = lunchFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.dinnerRecycler.apply {
            adapter = dinnerFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.otherRecycler.apply {
            adapter = otherFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.breakfastAddFood.onSingleClick {
            startForResult<AddFoodActivity>(REQUEST_CODE) {
                putExtra("meal", "breakfast")
            }
        }

        binding.lunchAddFood.onSingleClick {
            startForResult<AddFoodActivity>(REQUEST_CODE) {
                putExtra("meal", "lunch")
            }
        }

        binding.dinnerAddFood.onSingleClick {
            startForResult<AddFoodActivity>(REQUEST_CODE) {
                putExtra("meal", "dinner")
            }
        }

        binding.otherAddFood.onSingleClick {
            startForResult<AddFoodActivity>(REQUEST_CODE) {
                putExtra("meal", "other")
            }
        }
    }

    private fun initChart() {
        val chart = binding.halfPieChart
        chart!!.setBackgroundColor(Color.WHITE)

        //chart!!.setUsePercentValues(true)
        chart!!.description.isEnabled = false

        chart!!.setCenterTextTypeface(
            Typeface.createFromAsset(
                context?.assets,
                "OpenSans-Light.ttf"
            )
        )

        chart!!.isDrawHoleEnabled = true
        chart!!.setHoleColor(Color.WHITE)

        chart!!.setTransparentCircleColor(Color.WHITE)
        chart!!.setTransparentCircleAlpha(110)

        chart!!.holeRadius = 58f
        chart!!.transparentCircleRadius = 61f

        chart!!.setDrawCenterText(true)

        chart!!.isRotationEnabled = false
        chart!!.isHighlightPerTapEnabled = true

        chart!!.maxAngle = 180f // HALF CHART
        chart!!.rotationAngle = 180f
        chart!!.setCenterTextOffset(0f, -20f)

        chart!!.animateY(1400, Easing.EaseInOutQuad)

        val l = chart!!.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        chart!!.setEntryLabelColor(Color.BLACK)
        chart!!.setEntryLabelTypeface(
            Typeface.createFromAsset(
                context?.assets,
                "OpenSans-Regular.ttf"
            )
        )
        chart!!.setEntryLabelTextSize(12f)
    }

    private fun generateCenterSpannableText(text: String): SpannableString {
        val s = SpannableString(text)
        s.setSpan(RelativeSizeSpan(1.7f), 0, s.length, 0)
        return s
    }

    private fun setData(foodEntriesModel: FoodEntriesModel) {
        val chart = binding.halfPieChart

        chart!!.centerText =
            generateCenterSpannableText("Daily Goal:\n${foodEntriesModel.dailyGoal} cal")

        val values = ArrayList<PieEntry>().apply {
            if (foodEntriesModel.totalCal() > 0) {
                add(PieEntry(foodEntriesModel.totalCal().toFloat(), "Food"))
            }

            val remaining = (foodEntriesModel.dailyGoal - foodEntriesModel.totalCal()).toFloat()
            if (remaining > 0)
                add(
                    PieEntry(
                        (foodEntriesModel.dailyGoal - foodEntriesModel.totalCal()).toFloat(),
                        "Remaining"
                    )
                )
        }

        val dataSet = PieDataSet(values, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(CalorieFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        data.setValueTypeface(
            Typeface.createFromAsset(
                context?.assets,
                "OpenSans-Light.ttf"
            )
        )
        chart!!.data = data

        chart!!.invalidate()
    }

    private fun observeViewModel() {
        viewModel.init()
        viewModel.breakfastFoodEntries.observe(viewLifecycleOwner, Observer {
            setData(it)
            breakfastFoodEntryAdapter.submitList(it.breakfast)
            lunchFoodEntryAdapter.submitList(it.lunch)
            dinnerFoodEntryAdapter.submitList(it.dinner)
            otherFoodEntryAdapter.submitList(it.other)
            binding.breakfastTotalCalories.text =
                getString(R.string.cal, it.breakfastTotalCal.toString())
            binding.lunchTotalCalories.text =
                getString(R.string.cal, it.lunchTotalCal.toString())
            binding.dinnerTotalCalories.text =
                getString(R.string.cal, it.dinnerTotalCal.toString())
            binding.otherTotalCalories.text =
                getString(R.string.cal, it.otherTotalCal.toString())
        })
    }

    private fun deleteFoodEntry(foodEntryId: Long?) {
        MaterialDialog(requireContext()).show {
            title(text = "Delete Confirmation")
            message(text = "Are you sure you want to delete this food entry?")
            positiveButton { foodEntryId?.let { viewModel.deleteFoodEntry(it) } }
            negativeButton()
            lifecycleOwner(viewLifecycleOwner)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE)
            viewModel.init()
        else
            super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val REQUEST_CODE = 9000
    }
}

inline fun <reified A : Activity> Fragment.startForResult(
    requestCode: Int,
    configIntent: Intent.() -> Unit = {}
) {
    startActivityForResult(Intent(activity, A::class.java).apply(configIntent), requestCode)
}
