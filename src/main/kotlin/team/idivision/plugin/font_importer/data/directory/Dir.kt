package team.idivision.plugin.font_importer.data.directory


sealed class Dir(val name: String) {
    data object Res : Dir(name = "res")
    data object Font : Dir(name = "font")
}