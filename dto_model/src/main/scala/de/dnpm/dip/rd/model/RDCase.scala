package de.dnpm.dip.rd.model


import java.time.LocalDate
import cats.Applicative
import de.dnpm.dip.model.{
  Id,
  ExternalId,
  Reference,
  Patient,
}
import play.api.libs.json.Json


final case class Clinician
(
  name: String
)

object Clinician
{

  implicit val format =
    Json.format[Clinician]
}

final case class RDCase
(
  id: Id[RDCase],
  externalId: ExternalId[RDCase],
  gestaltMatcherId: Option[ExternalId[RDCase]], 
  face2geneId: Option[ExternalId[RDCase]], 
  patient: Reference[Patient],
  recordedOn: Option[LocalDate],
  referrer: Clinician,
  reason: Reference[RDDiagnosis],
)

object RDCase
{

  implicit val format =
    Json.format[RDCase]
}