package com.lans.sleep_care.presentation.navigation

sealed class Route(val route: String) {
    data object LoginScreen: Route("login")
    data object RegisterScreen: Route("register")
    data object ForgotScreen: Route("forgot")
    data object VerificationScreen: Route("verification")
    data object HomeScreen: Route("home")
    data object ProfileScreen: Route("profile")
    data object ChangePasswordScreen: Route("change_password")
    data object PsychologistScreen: Route("psychologist")
    data object PsychologistDetailScreen: Route("psychologist_detail")
    data object MyTherapyScreen: Route("therapy")
    data object ChatRoomScreen: Route("chat_room")
    data object LogbookScreen: Route("logbook")
    data object SleepDiaryScreen: Route("sleep_diary")
    data object IdentifyValueScreen: Route("identify_value")
    data object ThoughtRecordScreen: Route("thought_record")
    data object EmotionRecordScreen: Route("emotion_record")
    data object CommittedActionScreen: Route("committed_action")
    data object ChatbotScreen: Route("chatbot")
    data object HistoryScreen: Route("history")
    data object HistoryDetailScreen: Route("history_detail")
}