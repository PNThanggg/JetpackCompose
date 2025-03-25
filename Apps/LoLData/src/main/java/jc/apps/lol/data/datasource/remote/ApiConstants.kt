package jc.apps.lol.data.datasource.remote

object ApiConstants {
    private const val BASE_SUB_URL = "https://ddragon.leagueoflegends.com/cdn/"
    private const val VERSION = "15.6.1"
    private const val LANGUAGE = "vi_VN"
    const val BASE_URL = "${BASE_SUB_URL}${VERSION}/data/${LANGUAGE}/"

    const val IMAGE_SPLASH_URL = "${BASE_SUB_URL}img/champion/splash/"
    const val IMAGE_LOADING_URL = "${BASE_SUB_URL}img/champion/loading/"
    const val IMAGE_SQUARE_URL = "${BASE_SUB_URL}${VERSION}/img/champion/"
    const val IMAGE_PASSIVE_URL = "${BASE_SUB_URL}${VERSION}/img/passive/"
    const val IMAGE_ABILITY_URL = "${BASE_SUB_URL}${VERSION}/img/spell/"
}