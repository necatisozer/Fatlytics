package com.fatlytics.app.ui.main.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import androidx.lifecycle.Observer
import com.fatlytics.app.R
import com.fatlytics.app.data.source.api.FoodEntriesModel
import com.fatlytics.app.databinding.HomeFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import java.util.ArrayList

class HomeFragment : BaseViewModelFragment<HomeViewModel, HomeFragmentBinding>() {
    override val layoutRes = R.layout.home_fragment
    override val viewModelClass = HomeViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.pieChart.apply {
            //setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)

            dragDecelerationFrictionCoef = 0.95f

            setCenterTextTypeface(Typeface.createFromAsset(context?.assets, "OpenSans-Light.ttf"))
            centerText = generateCenterSpannableText()

            //isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setDrawRoundedSlices(true)

            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)

            holeRadius = 58f
            transparentCircleRadius = 61f

            setDrawCenterText(true)

            rotationAngle = 0f
            // enable rotation of the chart by touch
            isRotationEnabled = true
            isHighlightPerTapEnabled = true

            // setUnit(" €");
            // setDrawUnitsInChart(true);

            // add a selection listener
            // setOnChartValueSelectedListener(this)

            animateY(1400, Easing.EaseInOutQuad)
            // chart.spin(2000, 0, 360);

            val l = legend
            l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            l.orientation = Legend.LegendOrientation.VERTICAL
            l.setDrawInside(false)
            l.xEntrySpace = 7f
            l.yEntrySpace = 0f
            l.yOffset = 0f

            // entry label styling
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTypeface(Typeface.createFromAsset(context?.assets, "OpenSans-Regular.ttf"))
            setEntryLabelTextSize(12f)
        }
    }

    private fun generateCenterSpannableText(): SpannableString {

        val s = SpannableString("Daily Info")
        s.setSpan(RelativeSizeSpan(1.7f), 0, s.length, 0)
        return s
    }

    private fun observeViewModel() {
        viewModel.foodEntries.observe(viewLifecycleOwner, Observer { setData(it) })
    }

    private fun setData(foodEntriesModel: FoodEntriesModel) {
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        val entries = ArrayList<PieEntry>().apply {
            if (foodEntriesModel.breakfastTotalCal > 0) add(
                PieEntry(
                    foodEntriesModel.breakfastTotalCal.toFloat(),
                    "Breakfast"
                )
            )
            if (foodEntriesModel.lunchTotalCal > 0) add(
                PieEntry(
                    foodEntriesModel.lunchTotalCal.toFloat(),
                    "Lunch"
                )
            )
            if (foodEntriesModel.dinnerTotalCal > 0) add(
                PieEntry(
                    foodEntriesModel.dinnerTotalCal.toFloat(),
                    "Dinner"
                )
            )
            if (foodEntriesModel.otherTotalCal > 0) add(
                PieEntry(
                    foodEntriesModel.otherTotalCal.toFloat(),
                    "Snacks"
                )
            )
            if (isEmpty()) {
                add(
                    PieEntry(
                        1f,
                        "No Meals"
                    )
                )
            }
        }

        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(CalorieFormatter(binding.pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        data.setValueTypeface(Typeface.createFromAsset(context?.assets, "OpenSans-Light.ttf"))

        binding.pieChart.data = data

        // undo all highlights
        binding.pieChart.highlightValues(null)

        binding.pieChart.invalidate()
    }
}

class CalorieFormatter() : ValueFormatter() {

    private var pieChart: PieChart? = null

    // Can be used to remove percent signs if the chart isn't in percent mode
    constructor(pieChart: PieChart) : this() {
        this.pieChart = pieChart
    }

    override fun getFormattedValue(value: Float): String {
        return "${value.toInt()} cal"
    }

    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
        return getFormattedValue(value)
    }
}