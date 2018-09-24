using ParserLibrary;
using System;
using System.Collections.Generic;
using System.Numerics;
using System.Text;

namespace Server
{
    public class Server
    {

        static Queue<string> F1 = new Queue<string>();
        static Queue<string> F2 = new Queue<string>();
        static Queue<string> F3 = new Queue<string>();
        static Dictionary<BigInteger, byte[]> keyValuePairs = new Dictionary<BigInteger, byte[]>();
        ServerSocket ss;
        public Server()
        {
            ss = new ServerSocket();
        }

        public void Listen()
        {
            while (true)
            {
                Console.WriteLine("Queue1:" + ss.data);
                F1.Enqueue(ss.data);
            }
        }

        public void Persist()
        {
            while (true)
            {
                try
                {
                    string message = F2.Dequeue();
                    if (message != CommandTypes.Read)
                    {
                        FileHelper.WriteFile("C:\\Users\\mario\\Documents\\SD\\facomsd\\bkp.txt", message);
                    }
                }
                catch (InvalidOperationException ioe)
                {
                    Console.WriteLine("Unexpected exception : {0}", ioe.ToString());
                }
            }
        }

        public void Restore()
        {
            try
            {
                string bkp = FileHelper.ReadFile("C:\\Users\\mario\\Documents\\SD\\facomsd\\bkp.txt");
                CreateRegister(bkp);
            }
            catch (InvalidOperationException ioe)
            {
                Console.WriteLine("Unexpected exception : {0}", ioe.ToString());
            }
        }

        public void Replicate()
        {
            while (true)
            {
                try
                {
                    string message = F1.Dequeue();
                    F2.Enqueue(message);
                    F3.Enqueue(message);
                }
                catch (InvalidOperationException ioe)
                {
                    Console.WriteLine("Unexpected exception : {0}", ioe.ToString());
                }
            }
        }


        void CreateRegister(string command)
        {
            keyValuePairs.Add(1, Encoding.ASCII.GetBytes(command));
        }

        string ReadRegister()
        {
            return Encoding.ASCII.GetString(keyValuePairs.ContainsKey(1) ? keyValuePairs[1] : new byte[1]);
        }

        void UpdateRegister(string command)
        {
            if (keyValuePairs.ContainsKey(1))
            {
                keyValuePairs[1] = Encoding.ASCII.GetBytes(command);
            }
            else
            {
                ss.Send("Error: Value not found");
            }

        }

        void DeleteRegister(string command)
        {

        }

        public void Execute()
        {
            string command = "";
            while (true)
            {
                try
                {
                    command = F3.Dequeue();
                }
                catch (InvalidOperationException ioe)
                {
                    Console.WriteLine("Unexpected exception : {0}", ioe.ToString());
                }

                switch (command)
                {
                    case CommandTypes.Create:
                        CreateRegister(command);
                        break;

                    case CommandTypes.Read:
                        ReadRegister();
                        break;

                    case CommandTypes.Update:
                        UpdateRegister(command);
                        break;

                    case CommandTypes.Delete:
                        DeleteRegister(command);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
