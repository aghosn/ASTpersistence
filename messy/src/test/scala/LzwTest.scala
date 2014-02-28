package messy.test

import org.scalatest.FlatSpec
import org.scalatest._
import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @brief test the compression Lzw for files (simple implementation from the original configuration)
 */
class LzwTest extends FlatSpec {
  import messy.Lzw._

  /**
   * Test Lzw with the loremIpsum.txt file : input and output should be the same.
   */
  it should "compress the loremIpsum" in {
    val in = new File("messy/src/test/resources/loremIpsum.txt")
    val compressed = new File("compressed.txt")
    val out = new File("out.txt")

    /* Let's compress it */
    compress(new DataInputStream(new FileInputStream(in)), new DataOutputStream(new FileOutputStream(compressed)))

    /* Let's decompress it */
    decompress(new DataInputStream(new FileInputStream(compressed)), new DataOutputStream(new FileOutputStream(out)))

    /* Let's now check the decompressed file. It should be lossless */
    val (st1, st2) = (new FileInputStream(in), new FileInputStream(out))

    while (st1.available > 0 && st2.available > 0) assert(st1.read == st2.read)
    assert(st1.available == st2.available)
  }

  /**
   * Calculate the compression %.
   */
  it should "have a good compression ?" in {
    val in = new File("messy/src/test/resources/loremIpsum.txt")
    val compressed = new File("compressed.txt")

    /* Let's compress it */
    compress(new DataInputStream(new FileInputStream(in)), new DataOutputStream(new FileOutputStream(compressed)))

    /* And let's compare the size */
    println("Testing Lzw compression...")
    println("Original file size : " + in.length)
    println("Compressed file size : " + compressed.length)
    println("Percent of the compressed file (100% is the original size) : " + compressed.length.toDouble / in.length)

    assert(true) /* Dummy */
  }
  
  /**
   * Just to remove the compressed and the output file.
   */
  it should "simply remove the compreesed and output file" in {
    val compressed = new File("compressed.txt")
    val out = new File("out.txt")
    assert(compressed.delete())
    assert(out.delete())
  }

}
