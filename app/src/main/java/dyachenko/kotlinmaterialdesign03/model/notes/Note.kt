package dyachenko.kotlinmaterialdesign03.model.notes

import java.util.*
import kotlin.random.Random

data class Note(
    val id: String,
    val created: Date,
    var title: String,
    var body: String,
    var isBodyVisible: Boolean = false
) {

    companion object {
        fun getDefValues() = listOf(
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody()),
            Note(genId(), Date(), genTitle(), genBody())
        )

        private val titles = arrayOf(
            "Новая парадигма реальности: младая поросль матереет!",
            "Оказывается, коронованный герцог графства определил дальнейшее развитие",
            "Воистину радостный звук: глас грядущего поколения",
            "Да, кровь стынет в жилах",
            "Частотность поисковых запросов сделала своё дело",
            "Мелочь, а приятно: зима близко",
            "Цена вопроса не важна, когда объемы выросли",
            "На двадцатом съезде партии прозвучало поразительное заявление: сложившаяся структура организации станет частью наших традиций"
        )

        private val bodies = arrayOf(
            "Но явные признаки победы институционализации являются только методом политического участия и в равной степени предоставлены сами себе. Действия представителей оппозиции представляют собой не что иное, как квинтэссенцию победы маркетинга над разумом и должны быть призваны к ответу. Равным образом, новая модель организационной деятельности позволяет выполнить важные задания по разработке дальнейших направлений развития.",
            "Следует отметить, что выбранный нами инновационный путь способствует повышению качества дальнейших направлений развития. Ясность нашей позиции очевидна: убеждённость некоторых оппонентов предопределяет высокую востребованность экспериментов, поражающих по своей масштабности и грандиозности! Таким образом, понимание сути ресурсосберегающих технологий создаёт предпосылки для системы обучения кадров, соответствующей насущным потребностям.",
            "Идейные соображения высшего порядка, а также экономическая повестка сегодняшнего дня играет определяющее значение для приоритизации разума над эмоциями. Равным образом, высококачественный прототип будущего проекта требует определения и уточнения модели развития.",
            "Безусловно, сложившаяся структура организации напрямую зависит от позиций, занимаемых участниками в отношении поставленных задач. В частности, новая модель организационной деятельности позволяет оценить значение инновационных методов управления процессами.",
            "Принимая во внимание показатели успешности, базовый вектор развития представляет собой интересный эксперимент проверки переосмысления внешнеэкономических политик. Безусловно, понимание сути ресурсосберегающих технологий прекрасно подходит для реализации системы обучения кадров, соответствующей насущным потребностям.",
            "Таким образом, высокотехнологичная концепция общественного уклада создаёт необходимость включения в производственный план целого ряда внеочередных мероприятий с учётом комплекса анализа существующих паттернов поведения. Идейные соображения высшего порядка, а также курс на социально-ориентированный национальный проект прекрасно подходит для реализации глубокомысленных рассуждений.",
            "Но выбранный нами инновационный путь создаёт необходимость включения в производственный план целого ряда внеочередных мероприятий с учётом комплекса поставленных обществом задач. Высокий уровень вовлечения представителей целевой аудитории является четким доказательством простого факта: реализация намеченных плановых заданий однозначно фиксирует необходимость кластеризации усилий.",
            "Господа, выбранный нами инновационный путь обеспечивает актуальность новых предложений. В рамках спецификации современных стандартов, многие известные личности призваны к ответу."
        )

        fun genTitle(): String {
            val rand = Random.nextInt(titles.size)
            return titles[rand]
        }

        fun genBody(): String {
            val rand = Random.nextInt(bodies.size)
            return bodies[rand]
        }

        fun genId() = UUID.randomUUID().toString()
    }
}
