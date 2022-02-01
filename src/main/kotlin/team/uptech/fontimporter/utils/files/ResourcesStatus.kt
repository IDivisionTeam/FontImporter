package team.uptech.fontimporter.utils.files

sealed class ResourcesStatus {
    object NoResDir : ResourcesStatus()
    object NoFontDir : ResourcesStatus()
    object Success : ResourcesStatus()
}