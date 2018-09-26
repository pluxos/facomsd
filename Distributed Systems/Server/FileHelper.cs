using System;
using System.IO;
using System.Text;

namespace Server
{
    public static class FileHelper
    {
        public static string[] ReadFile(string path)
        {
            try
            {
                var lines = File.ReadAllLines(path);
                return lines;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
                return null;
            }
        }

        public static void WriteFile(string path, string text)
        {
            try
            {
                //Pass the filepath and filename to the StreamWriter Constructor
                StreamWriter sw = new StreamWriter(path, true, Encoding.ASCII);

                //Write a line of text
                sw.WriteLine(text);

                //Close the file
                sw.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
            }
        }
    }
}
