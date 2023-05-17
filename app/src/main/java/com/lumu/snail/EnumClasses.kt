package com.lumu.snail

enum class Category(
    var chapterList: List<String>
){
    Subjects(listOf(
        "Algebra", "Analysis", "Smooth Manifolds", "Algebraic Topology"
    )),
    Topics(listOf(
        "Field Theory", "Group Theory", "Hilbert Spaces",
        "Lp-Spaces", "Measure Theory",
        "Modules and Vector Spaces",
        "Metric Spaces", "Normed Spaces",
        "Representation Theory", "Ring Theory",
        "Singular Homology", "The Fundamental Group", "Vector Bundles"
    )),
    Exercises(listOf(
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
