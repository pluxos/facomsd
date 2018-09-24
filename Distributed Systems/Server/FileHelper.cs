using System;
using System.IO;
using System.Text;

namespace Server
{
    public static class FileHelper
    {
        public static string ReadFile(string path)
        {
            string line;
            try
            {
                //Pass the file path and file name to the StreamReader constructor
                StreamReader sr = new StreamReader(path);

                //Read the first line of text
                line = sr.ReadLine();

                //Continue to read until you reach end of file
                while (line != null)
                {
                    //Read the next line
                    line = sr.ReadLine();
                }
                //close the file
                sr.Close();
                return line;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
                return "";
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
