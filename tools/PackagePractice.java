import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import java.io.*;
class PackagePractice {
 public static void main(String[] args) throws Exception {
  if (args.length != 1 || !args[0].matches("[0-9]{9}")) {
   System.err.println("Use java tools/PackagePractice.java followed by your nine-digit student number.");
   System.exit(2);
  }
  for (String n : List.of("src", "test", "DESIGN.md", "results.txt", "README.md"))
   if (!Files.exists(Path.of(n))) throw new IOException("Missing " + n + ". Run tests and complete the practice first.");
  String stem="LT2_Practice_"+args[0]; Path target=Path.of(stem+".zip"); int i=2;
  while (Files.exists(target)) target=Path.of(stem+"_"+i+++".zip");
  List<Path> files=new ArrayList<>();
  for (String n : List.of("src","test","tools")) try(var s=Files.walk(Path.of(n))) {
   s.filter(Files::isRegularFile).filter(p->p.toString().endsWith(".java")).sorted().forEach(files::add);
  }
  for(String n:List.of("DESIGN.md","results.txt","README.md","run-tests.sh","run-tests.cmd")) files.add(Path.of(n));
  try(ZipOutputStream z=new ZipOutputStream(Files.newOutputStream(target,StandardOpenOption.CREATE_NEW))) {
   for(Path p:files) { z.putNextEntry(new ZipEntry(p.toString().replace('\\','/'))); Files.copy(p,z); z.closeEntry(); }
  }
  try(ZipFile z=new ZipFile(target.toFile())) {
   System.out.println("Created and reopened: "+target); z.stream().forEach(e->System.out.println(e.getName()));
  }
  System.out.println("This command does not upload work. Upload the ZIP on the optional eClass practice activity.");
 }
}
