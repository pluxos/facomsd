using System;
using System.Collections.Generic;
using System.Numerics;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;

namespace Client
{
    class Client
    {
        ClientSocket cs;

        public Client()
        {
            cs = new ClientSocket();
        }

        public void Listen()
        {
            cs.ReceiveMessage();
        }

        bool IsValidAnswer(string aws)
        {
            string[] splitAws = aws.Split(" ");

            if (string.Compare(splitAws[0], "Create") == 0 || string.Compare(splitAws[0], "Update") == 0)
            {
                if (splitAws.Length == 3)
                {
                    if (int.TryParse(splitAws[1], out int n) && new Regex("^[a-zA-Z0-9]*$").IsMatch(splitAws[2]))
                    {
                        cs.SendMessage(aws);
                        return true;
                    }
                }
                else if (splitAws.Length == 2)
                {
                    if (int.TryParse(splitAws[1], out int n))
                    {
                        cs.SendMessage(aws);
                        return true;

                    }
                }
            }
            else if(string.Compare(splitAws[0], "Read") == 0 || string.Compare(splitAws[0], "Delete") == 0)
            {
                if (splitAws.Length == 2)
                {
                    if (int.TryParse(splitAws[1], out int n))
                    {
                        cs.SendMessage(aws);
                        return true;

                    }
                }
            }
            return false;
        }

        public void ReadIO()
        {
            while (true)
            {
                Console.Clear();
                Console.WriteLine("Welcome to the SKYNET, type <exit> human and prepare yourself for the judgment day:");
                Console.WriteLine("Create <key> <value>");
                Console.WriteLine("Read <key>");
                Console.WriteLine("Update <key> <value>");
                Console.WriteLine("Delete <key>");
                string aws = Console.ReadLine();
                Console.Clear();
                if (string.Compare(aws, "exit") == 0)
                {
                    break;
                }
                if (!IsValidAnswer(aws))
                {
                    Console.WriteLine("Comando inválido, tente novamente!");
                    Thread.Sleep(1000);
                }
            }
        }
    }
}
