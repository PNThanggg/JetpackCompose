package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Runs(
    @SerializedName("runs") val runs: List<Run>?,
)

data class Run(
    @SerializedName("text") val text: String,
    @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint?,
)

fun List<Run>.splitBySeparator(): List<List<Run>> {
    val res = mutableListOf<List<Run>>()
    var tmp = mutableListOf<Run>()
    forEach { run ->
        if (run.text == " • ") {
            res.add(tmp)
            tmp = mutableListOf()
        } else {
            tmp.add(run)
        }
    }
    res.add(tmp)
    return res
}

fun List<List<Run>>.clean(): List<List<Run>> =
    if (getOrNull(0)?.getOrNull(0)?.navigationEndpoint != null) this
    else this.drop(1)

fun List<Run>.oddElements() = filterIndexed { index, _ ->
    index % 2 == 0
}
