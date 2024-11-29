package de.dnpm.dip.rd.model


import java.time.{
  LocalDate,
  YearMonth
}
import de.dnpm.dip.coding.{
  Coding,
  CodedEnum,
  DefaultCodeSystem,
}
import de.dnpm.dip.model.{
  Id,
  Reference,
  Patient,
  Observation
}
import play.api.libs.json.{
  Json,
  OFormat
}



final case class HPOTerm
(
  id: Id[HPOTerm],
  patient: Reference[Patient],
  onsetDate: YearMonth,
  value: Coding[HPO],
  statusHistory: Option[List[HPOTerm.Status]]
)
extends Observation[Coding[HPO]]

object HPOTerm
{

  object Status
  extends CodedEnum("dnpm-dip/rd/hpo-term/status")
  with DefaultCodeSystem
  {

    val Improved = Value("improved")
    val Worsened = Value("worsened")
    val Resolved = Value("resolved")

    override val display =
      Map(
        Improved -> "Verbessert",
        Worsened -> "Verschlechtert",
        Resolved -> "Weggefallen"
      )

  }

  final case class Status
  (
    status: Coding[Status.Value],
    date: LocalDate
  )


  import de.dnpm.dip.util.json._

  implicit val formatStatus: OFormat[Status] =
    Json.format[Status]

  implicit val format: OFormat[HPOTerm] =
   Json.format[HPOTerm]
}


final case class Autozygosity
(
  id: Id[Autozygosity],
  patient: Reference[Patient],
  value: Float
)
extends Observation[Float]

object Autozygosity
{
   implicit val format: OFormat[Autozygosity] =
    Json.format[Autozygosity]
}


