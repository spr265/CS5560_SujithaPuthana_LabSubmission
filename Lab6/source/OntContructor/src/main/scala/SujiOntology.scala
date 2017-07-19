
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat
import java.io.FileOutputStream
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.util.DefaultPrefixManager
import org.semanticweb.owlapi.model.IRI
import scala.io.Source
/**
  * Created by putha on 7/16/2017.
  */
object SujiOntology
{
  def main (args :Array[String]):Unit =
  {
    val sujiONTOLOGYURI="http://www.semanticweb.org/puthana/Sujiontologies"
    val sujimanager = OWLManager.createOWLOntologyManager
    val sujidf = sujimanager.getOWLDataFactory

    val sujiontology = sujimanager.createOntology(IRI.create(sujiONTOLOGYURI,"sujifamily#"))
    val sujipm = new DefaultPrefixManager(null, null, sujiONTOLOGYURI+"sujifamily#")
    val sujiclasses=Source.fromFile("data/Classes").getLines()

    sujiclasses.foreach(fsuji=>{
      val sujicls = sujidf.getOWLClass(fsuji, sujipm)
      val sujideclarationAxiomcls= sujidf.getOWLDeclarationAxiom(sujicls)
      sujimanager.addAxiom(sujiontology, sujideclarationAxiomcls)
    })

    val sujisubClasses=Source.fromFile("Sujidata/SujiSubClasses").getLines()

      sujisubClasses.foreach(fsuji=>{
      val sujifarr=fsuji.split(",")
      val sujicls=sujidf.getOWLClass(sujifarr(0), sujipm)
      val sujisubCls=sujidf.getOWLClass(sujifarr(1), sujipm)
      val sujideclarationAxiom = sujidf.getOWLSubClassOfAxiom(sujisubCls, sujicls)
      sujimanager.addAxiom(sujiontology, sujideclarationAxiom)
    }
      )

    val sujiobjprop=Source.fromFile("Sujidata/SujiObjectProperties").getLines()
    sujiobjprop.foreach(fsuji=> {
      val sujifarr=fsuji.split(",")
      val sujidomain = sujidf.getOWLClass(sujifarr(1), sujipm)
      val sujirange = sujidf.getOWLClass(sujifarr(2), sujipm)
      val sujiobjpropaxiom = sujidf.getOWLObjectProperty(sujifarr(0), sujipm)
      val sujirangeAxiom = sujidf.getOWLObjectPropertyRangeAxiom(sujiobjpropaxiom, sujirange)
      val sujidomainAxiom = sujidf.getOWLObjectPropertyDomainAxiom(sujiobjpropaxiom, sujidomain)
      sujimanager.addAxiom(sujiontology, sujirangeAxiom)
      sujimanager.addAxiom(sujiontology, sujidomainAxiom)
      if(sujifarr(3)=="SujiFunc")
        sujimanager.addAxiom(sujiontology, sujidf.getOWLFunctionalObjectPropertyAxiom(sujiobjpropaxiom))
      else if(sujifarr(3).contains("SujiInverseOf"))
      {
        val sujiinverse=sujifarr(3).split(":")
        val sujiinverseaxiom = sujidf.getOWLObjectProperty(sujiinverse(1), sujipm)
        val sujirangeAxiom = sujidf.getOWLObjectPropertyRangeAxiom(sujiinverseaxiom, sujidomain)
        val sujidomainAxiom = sujidf.getOWLObjectPropertyDomainAxiom(sujiinverseaxiom, sujirange)
        sujimanager.addAxiom(sujiontology, sujirangeAxiom)
        sujimanager.addAxiom(sujiontology, sujidomainAxiom)
        sujimanager.addAxiom(sujiontology, sujidf.getOWLInverseObjectPropertiesAxiom(sujiobjpropaxiom, sujiinverseaxiom))
      }
    }
    )

    val sujidataprop=Source.fromFile("Sujidata/SujiDataProperties").getLines()
    sujidataprop.foreach(fsuji=>{
      val sujifarr=fsuji.split(",")
      val sujidomain=sujidf.getOWLClass(sujifarr(1),sujipm)
      val sujifullName = sujidf.getOWLDataProperty(sujifarr(0), sujipm)
      val sujidomainAxiomfullName = sujidf.getOWLDataPropertyDomainAxiom(sujifullName, sujidomain)
      sujimanager.addAxiom(sujiontology, sujidomainAxiomfullName)
      if(sujifarr(2)=="Sujistring") {
        val sujistringDatatype = sujidf.getStringOWLDatatype ()
        val sujirangeAxiomfullName = sujidf.getOWLDataPropertyRangeAxiom(sujifullName, sujistringDatatype)
        sujimanager.addAxiom(sujiontology, sujirangeAxiomfullName)
      }
      else if(sujifarr(2)=="Sujiint")
      {
        val sujiDatatype = sujidf.getIntegerOWLDatatype ()
        val sujirangeAxiomfullName = sujidf.getOWLDataPropertyRangeAxiom(sujifullName, sujiDatatype)
        sujimanager.addAxiom(sujiontology, sujirangeAxiomfullName)
      }
    })

    val sujiindividuals=Source.fromFile("Sujidata/SujiIndividuals").getLines()

    sujiindividuals.foreach(fsuji=>{
      val sujifarr=fsuji.split(",")
      val sujicls=sujidf.getOWLClass(sujifarr(0), sujipm)
      val sujiind = sujidf.getOWLNamedIndividual(sujifarr(1), sujipm)
      val sujiclassAssertion = sujidf.getOWLClassAssertionAxiom(sujicls, sujiind)
      sujimanager.addAxiom(sujiontology, sujiclassAssertion)
    })

    val sujitriplets=Source.fromFile("Sujidata/SujiTriplets").getLines()
    sujitriplets.foreach(fsuji=>{
      val sujifarr=fsuji.split(",")
      val sujisub = sujidf.getOWLNamedIndividual(sujifarr(0), sujipm)

      if(sujifarr(3)=="SujiObject")
      {
        val sujipred=sujidf.getOWLObjectProperty(sujifarr(1),sujipm)
        val sujiobj=sujidf.getOWLNamedIndividual(sujifarr(2), sujipm)
        val sujiobjAsser = sujidf.getOWLObjectPropertyAssertionAxiom(sujipred,sujisub, sujiobj)
        sujimanager.addAxiom(sujiontology, sujiobjAsser)
      }
      else if(sujifarr(3)=="SujiData")
      {
        val sujipred=sujidf.getOWLDataProperty(sujifarr(1),sujipm)
        val sujidat=sujidf.getOWLLiteral(sujifarr(2))
        val sujidatAsser = sujidf.getOWLDataPropertyAssertionAxiom(sujipred,sujisub, sujidat)
        sujimanager.addAxiom(sujiontology, sujidatAsser)
      }
    })

    val sujios = new FileOutputStream("Sujidata/sujidata.owl")
    val sujiowlxmlFormat = new OWLXMLDocumentFormat
    sujimanager.saveOntology(sujiontology, sujiowlxmlFormat, sujios)
    System.out.println("Suji data Ontology done")



  }

}

