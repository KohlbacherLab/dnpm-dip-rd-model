package de.dnpm.dip.rd.model


import java.time.LocalDate
import cats.Applicative
import cats.data.NonEmptyList
import de.dnpm.dip.coding.{
  Coding,
  CodeSystem,
  CodedEnum,
  DefaultCodeSystem,
  CodeSystemProvider,
  CodeSystemProviderSPI,
  SingleCodeSystemProvider
}
import de.dnpm.dip.model.{
  Id,
  Reference,
  Patient,
  Diagnosis,
  Age
}
import play.api.libs.json.{
  Json,
  Format,
  OFormat
}



final case class RDDiagnosis
(
  id: Id[RDDiagnosis],
  patient: Reference[Patient],
  recordedOn: Option[LocalDate],
  categories: NonEmptyList[Coding[Any]],
  onsetAge: Option[Age],
  prenatal: Boolean,
  status: Coding[RDDiagnosis.Status.Value]
)
extends Diagnosis

object RDDiagnosis
{

  object Status
  extends CodedEnum("dnpm-dip/rd/diagnosis/status")
  with DefaultCodeSystem
  {

    val Solved          = Value("solved")
    val PartiallySolved = Value("partially-solved")
    val Unclear         = Value("unclear")
    val Unsolved        = Value("unsolved")

    implicit val format: Format[Status.Value] = 
      Json.formatEnum(this)

    override val display = {
      case Solved          => "Gelöst"
      case PartiallySolved => "Teilweise gelöst"
      case Unclear         => "Unklar"
      case Unsolved        => "Ungelöst"
    }

    final class ProviderSPI extends CodeSystemProviderSPI
    {
      override def getInstance[F[_]]: CodeSystemProvider[Any,F,Applicative[F]] =
        new Provider.Facade[F]
    }

  }


  import de.dnpm.dip.util.json._

  implicit val format: OFormat[RDDiagnosis] =
    Json.format[RDDiagnosis]
}
