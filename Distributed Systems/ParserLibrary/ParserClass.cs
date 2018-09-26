using System.Collections.Generic;
using System.IO;
using System.Numerics;
using System.Runtime.Serialization.Formatters.Binary;

namespace ParserLibrary
{
    public class ParserClass
    {

        public static byte[] Serialize<T>(T obj)
        {
            var stream = new MemoryStream();
            new BinaryFormatter().Serialize(stream, obj);
            return stream.ToArray();
        }

        public static T Deserialize<T>(byte[] stream)
        {
            var binaryFormatter = new BinaryFormatter();
            return (T)binaryFormatter.Deserialize(new MemoryStream(stream));
        }
    }
}
