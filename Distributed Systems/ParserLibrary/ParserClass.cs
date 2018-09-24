using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace ParserLibrary
{
    public class ParserClass
    {

        public static byte[] Serialize(object obj)
        {
            var stream = new MemoryStream();
            new BinaryFormatter().Serialize(stream, obj);
            return stream.ToArray();
        }

        public static string Deserialize(byte[] stream)
        {
            return new BinaryFormatter().Deserialize(new MemoryStream(stream)).ToString();
        }
    }
}
