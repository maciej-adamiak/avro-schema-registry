package dev.madamiak.registry.model

import spray.json.JsValue

case class SchemaEnrollment(strain: String, version: String, schema: JsValue)