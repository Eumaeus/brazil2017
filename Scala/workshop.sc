import scala.io.Source
import edu.holycross.shot.scm._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.seqcomp._

val cexData = Source.fromFile("../CEX/brazil-large.cex").getLines.mkString("\n")
val library = CiteLibrary(cexData,"#",",")

val corpus = library.textRepository.get.corpus

println("")
println("=============================================")
println("Texts in Library")
println("")
for (t <- library.textRepository.get.catalog.texts) {
   println(t)
	 println(t.urn)
	 println("")
 }
println("=============================================")
println("")

def printNode(n:CitableNode):Unit = { println(s"${n.urn}\t ${n.text}") }
def printCorpus(c:Corpus) = for (cn <- c.nodes) println(s"${cn.urn}\t ${cn.text}")
def printTexts(tr:TextRepository):Unit = {
	println("")
	println("=============================================")
	for (t <- tr.catalog.texts) {
		 println(t)
		 println(t.urn)
		 println("")
	}
	println("=============================================")
	println("")
}

case class CiteObjectJson(citeObject:Option[
  ( Map[String,String], 
    Map[String,String], 
    Vector[Map[String,String]] 
  )]
)

def makeCiteObjectJson(objectFound:CiteObject):CiteObjectJson = {
  val objectUrn:Map[String,String] = Map("urn" -> objectFound.urn.toString)
  val objectLabel:Map[String,String] = Map("label" -> objectFound.label)
  val objectPropertiesVector:Vector[Map[String,String]] = objectFound.propertyList.map( p => {
    val m = Map("propertyUrn" -> p.urn.toString, "propertyValue" -> p.propertyValue.toString )
    m
  })
  val objreply = CiteObjectJson( Some(objectUrn, objectLabel, objectPropertiesVector) )
  objreply
}

def psgRef(u:CtsUrn):String = {
  val psgRef = u.passageComponentOption match {
	      case None => ""
    	  case s: Option[String] => s.get    
  }
  psgRef
}

val citation_edition_1_10 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1-1.10")
val citation_edition_11_20 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.11-1.20")
val citation_edition_3_12 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.3-1.12")
val citation_edition_book1 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1")
val citation_edition_book1range = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1-1.611")
val citation_exemplarA_1_2 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2")
val citation_exemplarB_1_2 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.lemmata:1.2")


