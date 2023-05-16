package com.lumu.snail

enum class Category(
    var chapterList: List<String>
){
    Subjects(listOf(
        "Algebra", "Analysis", "Smooth Manifolds", "Algebraic Topology"
    )),
    Topics(listOf(
        "Representation Theory",
        "The Fundamental Group",
        "Hilbert Spaces", "Normed Spaces",
        "Singular Homology", "Vector Bundles"
    )),
    Exercises(listOf(
        "Metric Spaces",
        "The Fundamental Group", "Homology",
        "Field Theory", "Group Theory",
        "Lp-Spaces", "Measure Theory",
        "Modules and Vector Spaces",
        "Representation Theory", "Ring Theory",
    )),
    Everything(listOf(
    ))
}

enum class Subjects(
    var topics:List<String>
){
    Algebra(
        listOf(
            "Group Theory", "Field Theory",
            "Modules and Vector Spaces",
            "Representation Theory", "Ring Theory"
        )
    ),

    Analysis(
        listOf(
            "Metric Spaces", "Normed Spaces",
            "Hilbert Spaces"
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
