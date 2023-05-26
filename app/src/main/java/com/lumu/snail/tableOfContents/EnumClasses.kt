package com.lumu.snail.tableOfContents

enum class Category(
    var chapterList: List<String>
){
    Subjects(listOf(
        "Algebra", "Analysis", "Smooth Manifolds", "Algebraic Topology"
    )),
    Topics(listOf(
        "Basic Group Theory", "Field Theory", "Group Actions", "Hilbert Spaces",
        "Lp-Spaces", "Measure Theory",
        "Modules and Vector Spaces",
        "Metric Spaces", "Normed Spaces",
        "Representation Theory", "Ring Theory",
        "Singular Homology", "The Fundamental Group", "Vector Bundles",
        "Sylow Theorems", "ED, PID and UFD"
    )),
    Exercises(listOf(
    )),
    Miscellaneous(listOf(
        "Sage"
    ))
}

enum class Subjects(
    var topics:List<String>
){
    Algebra(
        listOf(
            "Group Actions", "Basic Group Theory", "Field Theory",
            "Modules and Vector Spaces",
            "Representation Theory", "Ring Theory",
            "Sylow Theorems"
        )
    ),

    Analysis(
        listOf(
            "Metric Spaces", "Normed Spaces",
            "Hilbert Spaces", "Measure Theory"
        )
    ),

    SmoothManifolds(
        listOf(
            "Topology", "Vector Bundles",
            "Differential Forms"
        )
    ),

    AlgebraicTopology(
        listOf(
            "The Fundamental Group", "Singular Homology"
        )
    );

    companion object {
        fun isSubjectName(name: String): Boolean {
            return try {
                Subjects.valueOf(name)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }
}
